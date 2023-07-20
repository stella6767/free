package com.stella.free.web.controller

import com.stella.free.web.component.auth.LoginModalViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class AuthController(
    private val loginModalViewComponent: LoginModalViewComponent
) {




    @GetMapping("/login/modal")
    fun loginModal(): ViewContext {

        return loginModalViewComponent.render()
    }


}