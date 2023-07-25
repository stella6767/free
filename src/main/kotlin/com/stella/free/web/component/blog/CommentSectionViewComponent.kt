package com.stella.free.web.component.blog

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder


@ViewComponent
class CommentSectionViewComponent(

) {

    fun render(): ViewContext {

        val authentication =
            SecurityContextHolder.getContext().authentication

        val isDisabled =
            authentication is AnonymousAuthenticationToken


        return ViewContext(
            "authentication" toProperty authentication,
            "isDisabled" toProperty isDisabled

        )
    }

}