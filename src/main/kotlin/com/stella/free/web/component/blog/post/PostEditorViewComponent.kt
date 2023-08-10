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

        val post = if (postId != 0L) {
            postService.findPostById(postId)
        } else Optional.ofNullable(null)


        val authentication =
            SecurityContextHolder.getContext().authentication

        val isLogin =
            authentication !is AnonymousAuthenticationToken

        val (username, userId) = if (isLogin) {
            val userPrincipal = authentication.principal as UserPrincipal
            Pair(userPrincipal.name,userPrincipal.user.id)
        }else Pair("", null)

        return ViewContext(
            "post" toProperty post,
            "tagNames" toProperty post.map { it.tagNames }.orElse("[]"),
            "isLogin" toProperty isLogin,
            "username" toProperty username,
            "userId" toProperty userId,

        )
    }

}