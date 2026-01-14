package freeapp.life.stella.api.web

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.UserService
import freeapp.life.stella.api.web.dto.UpdateProfileDto
import freeapp.life.stella.api.web.dto.UserDeleteRequestDto

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRedirectView
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRefreshView
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile


@RequestMapping("/user")
@Controller
class UserController(
    private val userService: UserService,
) {

    @HxRequest
    @DeleteMapping("")
    fun deleteUser(
        @AuthenticationPrincipal principal: UserPrincipal,
        deleteRequestDto: UserDeleteRequestDto,
    ): HtmxRedirectView {

        userService.deleteUser(
            principal.user.id,
            deleteRequestDto
        )
        return HtmxRedirectView("/")
    }


    @HxRequest
    @PutMapping("")
    fun updateUser(
        @AuthenticationPrincipal principal: UserPrincipal,
        profileDto: UpdateProfileDto,
        @RequestParam("profileImage", required = false) file: MultipartFile? = null,
        model: Model,
    ): HtmxRefreshView {

        userService.updateUser(principal.user.id, profileDto, file)

        return HtmxRefreshView()
    }


}
