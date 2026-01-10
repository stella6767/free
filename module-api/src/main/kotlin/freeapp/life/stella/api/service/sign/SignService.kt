package freeapp.life.stella.api.service.sign


import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.MailService
import freeapp.life.stella.api.util.generateVerifyCode
import freeapp.life.stella.api.util.generateVerifyToken
import freeapp.life.stella.api.web.dto.EmailBodyDto
import freeapp.life.stella.api.web.dto.EmailDto
import freeapp.life.stella.api.web.dto.EmailTemplate
import freeapp.life.stella.api.web.dto.LoginDto
import freeapp.life.stella.api.web.dto.ResendCodeDto
import freeapp.life.stella.api.web.dto.SignUpDto
import freeapp.life.stella.api.web.dto.SignUpResponseDto
import freeapp.life.stella.api.web.dto.VerifyDto
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.UserVerify
import freeapp.life.stella.storage.entity.type.SignType
import freeapp.life.stella.storage.repository.UserRepository
import freeapp.life.stella.storage.repository.UserVerifyRepository
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class SignService(
    private val userRepository: UserRepository,
    private val userVerifyRepository: UserVerifyRepository,
    private val mailService: MailService,
    private val encoder: PasswordEncoder,
) {

    private val log = KotlinLogging.logger {}
    private val expireMinute: Long = 10

    @Transactional
    fun login(
        loginDto: LoginDto,
        httpRequest: HttpServletRequest,
    ) {

        val user = userRepository.findActiveUserByEmailAndSignType(
            loginDto.email, SignType.EMAIL
        ) ?: throw UsernameNotFoundException("사용자를 찾을 수 없거나 계정이 활성화되지 않았습니다: ${loginDto.email}")

        if (!encoder.matches(loginDto.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        setAuthentication(user, httpRequest)
        user.updateLastLoginDate()
    }

    private fun setAuthentication(user: User, httpRequest: HttpServletRequest) {
        val userPrincipal = UserPrincipal(user)

        val authentication = UsernamePasswordAuthenticationToken(
            userPrincipal,
            user.password,
            userPrincipal.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
        // 5. 세션에 보안 컨텍스트 저장
        val session = httpRequest.getSession(true)

        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        )
    }


    @Transactional
    fun signUp(signUpDto: SignUpDto): SignUpResponseDto {

        checkIsNormalEmailAddressAvailable(signUpDto.email)

        val userVerify =
            userVerifyRepository.findLatestUserVerifyByEmail(signUpDto.email, LocalDateTime.now())
                ?: run {
                    val user =
                        userRepository.save(signUpDto.toEntity(encoder.encode(signUpDto.password)))
                    UserVerify(
                        user = user,
                        code = generateVerifyCode(),
                        verifyToken = generateVerifyToken(),
                        expiredAt = LocalDateTime.now().plusMinutes(expireMinute),
                    )
                }

        userVerifyRepository.save(userVerify)

        val emailDto = EmailDto(
            emailTemplate = EmailTemplate.VERIFICATION,
            body = EmailBodyDto(
                verificationCode = userVerify.code,
                validityMinutes = expireMinute.toString()
            )
        )

        mailService.sendEmailTemplate(signUpDto.email, emailDto)

        return SignUpResponseDto(
            signUpDto.email,
            expireMinute,
            userVerify.verifyToken
        )
    }


    @Transactional
    fun signUpWithEmailVerify(
        verifyDto: VerifyDto,
        httpRequest: HttpServletRequest,
    ) {

        //code와 token은 유효해야한다.
        //code와 token으로 꺼낸 이메일을 조회했을때, 유저는 회원가입이 가능해야한다.
        //code와 token은 해당 유저를 위해 제일 마지막에 발급된 토큰이어야 한다.
        log.debug { "코드 인증!" }

        val verify = userVerifyRepository.findUserVerifyByCodeAndToken(
            verifyToken = verifyDto.token,
            verifyCode = verifyDto.code
        ) ?: throw IllegalArgumentException("EMAIL TOKEN IS UNAUTHORIZED")

        //todo 빈 문자열
        checkIsNormalEmailAddressAvailable(verify.user.email ?: "")

        verify.verify()
        //회원가입 성공
        verify.user.status = User.Status.ACTIVATED

        setAuthentication(verify.user, httpRequest)
    }


    @Transactional
    fun resendCode(dto: ResendCodeDto): String {

        val userVerify =
            userVerifyRepository.findVerifyByUserEmailAndToken(
                email = dto.email,
                verifyToken = dto.token,
            ) ?: return "send failure"


        //update
        if (userVerify.expiredAt!!.isAfter(LocalDateTime.now())) {
            userVerify.expiredAt = LocalDateTime.now().plusMinutes(expireMinute)
            userVerify.code = generateVerifyCode()
        }

        val emailDto = EmailDto(
            emailTemplate = EmailTemplate.VERIFICATION,
            body = EmailBodyDto(
                verificationCode = userVerify.code,
                validityMinutes = expireMinute.toString()
            )
        )

        mailService.sendEmailTemplate(dto.email, emailDto)

        return "send success"
    }


    private fun checkIsNormalEmailAddressAvailable(email: String) {
        //일반 회원가입 시(소셜 X) 해당 이메일로 이미 가입이 되어 있으면 불가능.
        val users =
            userRepository.findUsersByEmailAndSignTypeAndStatus(
                email,
                SignType.EMAIL,
                User.Status.ACTIVATED
            )

        if (users.isNotEmpty()) {
            throw IllegalArgumentException("already exists email in database")
        }

    }


}
