package com.stella.free.web.component.blog.post

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@ViewComponent
class PostModalViewComponent(

) {
    fun render(postId:Long): ViewContext {

        val authentication =
            SecurityContextHolder.getContext().authentication

        return ViewContext(
            "postId" toProperty postId,
            "authentication" toProperty authentication

        )

    }

}