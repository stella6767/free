package com.stella.free.web.component.blog.post

import com.stella.free.core.blog.dto.PostDetailDto
import com.stella.free.core.blog.entity.Post
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@ViewComponent
class PostModalViewComponent(

) {
    fun render(post: PostDetailDto): ViewContext {

        val authentication =
            SecurityContextHolder.getContext().authentication

        return ViewContext(
            "post" toProperty post,
            "authentication" toProperty authentication

        )

    }

}