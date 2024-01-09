package com.stella.free.web.component.blog.post

import com.stella.free.core.blog.service.PostService
import com.stella.free.global.config.security.UserPrincipal
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


@ViewComponent
class PostEditorViewComponent(
    private val postService: PostService,
) {

    fun render(postId: Long): ViewContext {

        val post = postService.findPostById(postId)

        return ViewContext(
            "post" toProperty Optional.ofNullable(post),
            "tagNames" toProperty Optional.ofNullable(post).map { it.tagNames }.orElse("[]"),
        )
    }

}