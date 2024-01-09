package com.stella.free.web.component.blog.post

import com.stella.free.core.blog.service.PostService
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.web.component.blog.comment.CommentSectionViewComponent
import com.stella.free.web.component.blog.tag.TagViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder


@ViewComponent
class PostDetailViewComponent(
    private val postService: PostService,
    private val commentSectionViewComponent: CommentSectionViewComponent,
    private val tagViewComponent: TagViewComponent,
    private val postModalViewComponent: PostModalViewComponent,
) {

    fun render(id: Long): ViewContext {

        val postDetail =
            postService.findPostDetailById(id)

        val postTags =
            postService.findTagsByPostId(id)

        val authentication =
            SecurityContextHolder.getContext().authentication

        val userId = if (authentication !is AnonymousAuthenticationToken) {
            val userPrincipal = authentication.principal as UserPrincipal
            userPrincipal.user.id
        } else null

        return ViewContext(
            "post" toProperty postDetail,
            "commentSectionViewComponent" toProperty commentSectionViewComponent,
            "postTags" toProperty postTags,
            "tagViewComponent" toProperty tagViewComponent,
            "postModalViewComponent" toProperty postModalViewComponent,
            "userId" toProperty userId,
        )
    }

}