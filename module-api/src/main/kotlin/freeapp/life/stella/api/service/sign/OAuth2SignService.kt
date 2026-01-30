package freeapp.life.stella.api.service.sign



import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.web.dto.GithubAuth2UserInfo
import freeapp.life.stella.api.web.dto.GoogleAuth2UserInfo

import freeapp.life.stella.api.web.dto.OAuth2UserInfo
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.type.SignType
import freeapp.life.stella.storage.repository.UserRepository
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*


class OAuth2SignService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
) : DefaultOAuth2UserService() {

    private val log = KotlinLogging.logger { }

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        log.debug("OAuth 로그인 진행중................ ${userRequest.accessToken.tokenValue}")

        val oAuth2User =
            super.loadUser(userRequest)

        log.debug(
            """
            ${oAuth2User.attributes}
            """.trimIndent()
        )

        return processOAuth2User(userRequest, oAuth2User)
    }


    private fun processOAuth2User(userRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {

        val oAuth2UserInfo =
            getOAuth2UserInfo(userRequest.clientRegistration.clientName, oAuth2User)

        log.info("oAuth2UserInfo=>${oAuth2UserInfo.attributes}")

        var user =
            userRepository.findUserBySocialIdAndSignTypeAndStatus(
                socialId = oAuth2UserInfo.getSocialId(),
                signType = oAuth2UserInfo.signType,
                status = User.Status.ACTIVATED
            )

        val uuid = UUID.randomUUID()
        val encPassword =
            encoder.encode(uuid.toString())

        if (user == null) {

            log.debug("최초 사용자입니다. 자동 회원가입을 진행합니다.")

            val newUser =
                User(
                    socialId = oAuth2UserInfo.getSocialId(),
                    status = User.Status.ACTIVATED,
                    username = "",
                    email = oAuth2UserInfo.getEmail() ?: "",
                    password = encPassword,
                    signType = oAuth2UserInfo.signType,
                    rawData = oAuth2UserInfo.attributes.toString(),
                    profileImg = oAuth2UserInfo.getSocialPictureUrl() ?: ""
                )
            return UserPrincipal(userRepository.save(newUser))
        } else {
            //이미 회원가입이 완료됐다는 뜻(원래는 구글 정보가 변경될 수 있기 떄문에 update 해야되는데 지금은 안하겠음)
            log.debug("회원정보가 있습니다. 바로 로그인합니다.")
            return UserPrincipal(user)
        }

    }

    private fun getOAuth2UserInfo(
        clientName: String,
        oAuth2User: OAuth2User
    ): OAuth2UserInfo {

        log.debug("머로 로그인 됐지? $clientName")

        return when (clientName) {
            SignType.GOOGLE.clientName -> {
                GoogleAuth2UserInfo(oAuth2User.attributes)
            }
            SignType.GITHUB.clientName -> {
                GithubAuth2UserInfo(oAuth2User.attributes)
            }
            else -> {
                throw IllegalArgumentException("Unknown client name: $clientName")
            }
        }

    }


}
