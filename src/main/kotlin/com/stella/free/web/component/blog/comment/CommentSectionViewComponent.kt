package com.stella.free.web.component.blog.comment

import com.stella.free.core.blog.dto.PostDetailDto
import com.stella.free.core.blog.service.CommentService
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder


@ViewComponent
class CommentSectionViewComponent(
    private val commentService: CommentService,
    private val commentFormViewComponent: CommentFormViewComponent,
    private val commentCardViewComponent: CommentCardViewComponent,

    ) {

    private val log = logger()
    fun render(post: PostDetailDto): ViewContext {


        val comments =
            commentService.findCommentsByPostId(post.id).map { it.toCardDto() }


        //log.info("post=>{}", post)
        return ViewContext(
            "commentFormViewComponent" toProperty commentFormViewComponent,
            "commentCardViewComponent" toProperty commentCardViewComponent,
            "post" toProperty post,
            "comments" toProperty comments,
        )
    }

}