package freeapp.life.stella.api.web

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.UserService
import freeapp.life.stella.api.service.sign.SignService
import freeapp.life.stella.api.web.dto.LoginDto
import freeapp.life.stella.api.web.dto.ResendCodeDto
import freeapp.life.stella.api.web.dto.SignUpDto
import freeapp.life.stella.api.web.dto.VerifyDto
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRedirectView
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@RequestMapping("/auth")
@Controller
class AuthController(
    private val signService: SignService,
    private val userService: UserService,
) {


    private val log = KotlinLogging.logger { }


    @GetMapping("/sign")
    fun signPage(
        @RequestParam("isSignUp") isSignUp: Boolean = false,
        model: Model,
        htmxRequest: HtmxRequest
    ): String {
        model.addAttribute("isSignUp", isSignUp)

        log.debug { "hey!!! ${htmxRequest.isHtmxRequest}" }

        return "page/sign"
    }

    @GetMapping("/profile")
    fun profilePage(
        model: Model,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): String {

        model.addAttribute(
            "currentUser",
            userService.findUserById(principal.user.id)
        )

        return "page/profile"
    }


    @HxRequest
    @PostMapping("/login")
    fun login(
        model: Model,
        loginDto: LoginDto,
        httpRequest: HttpServletRequest,
    ): HtmxRedirectView {

        signService.login(loginDto, httpRequest)

        return HtmxRedirectView("/")
    }


    @HxRequest
    @PostMapping("/sign-up")
    fun signUp(
        model: Model,
        @Valid signUpDto: SignUpDto,
        htmxResponse: HtmxResponse
    ): String {

        val dto = signService.signUp(signUpDto)

        model.addAttribute("email", dto.email)
        model.addAttribute("token", dto.token)
        model.addAttribute("expireMinute", dto.expireMinute)

        return "component/auth/verifyCode"
    }

    @HxRequest
    @PostMapping("/verify-code")
    fun verify(
        model: Model,
        verifyDto: VerifyDto,
        httpRequest: HttpServletRequest
    ): HtmxRedirectView {

        signService.signUpWithEmailVerify(verifyDto, httpRequest)

        return HtmxRedirectView("/")
    }


    @ResponseBody
    @PostMapping("/resend-code")
    fun resendCode(
        model: Model,
        resendCodeDto: ResendCodeDto,
        httpRequest: HttpServletRequest
    ): String {

        return signService.resendCode(resendCodeDto)
    }


}