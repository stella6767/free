package com.stella.free.web.component.blog.comment

import com.stella.free.core.blog.dto.PostDetailDto
import com.stella.free.core.blog.service.CommentService
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class CommentSectionViewComponent(
    private val commentService: CommentService,
    private val commentFormViewComponent: CommentFormViewComponent,
    private val commentCardViewComponent: CommentCardViewComponent,
    private val commentReplyViewComponent: CommentReplyViewComponent,
    ) {

    private val log = logger()
    fun render(post: PostDetailDto): ViewContext {


        val commentsMap =
            commentService.findCommentsByPostId(post.id).groupBy { it.idAncestor }



        //log.info("post=>{}", post)
        return ViewContext(
            "commentFormViewComponent" toProperty commentFormViewComponent,
            "commentCardViewComponent" toProperty commentCardViewComponent,
            "commentReplyViewComponent" toProperty commentReplyViewComponent,
            "post" toProperty post,
            "commentsMap" toProperty commentsMap,
        )
    }

}