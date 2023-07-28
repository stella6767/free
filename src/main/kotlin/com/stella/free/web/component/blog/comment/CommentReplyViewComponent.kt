package com.stella.free.web.component.blog.comment

import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class CommentReplyViewComponent(
    private val commentFormViewComponent: CommentFormViewComponent,
) {
    private val log = logger()
    fun render(commentReplyViewComponent: CommentReplyViewComponent,
               childComment: CommentCardDto,
               parentCommentUsername: String,
               paddingLeft: Int,
               ): ViewContext {
        

        return ViewContext(
            "commentReplyViewComponent" toProperty commentReplyViewComponent,
            "commentFormViewComponent" toProperty commentFormViewComponent,
            "childComment" toProperty childComment,
            "parentCommentUsername" toProperty parentCommentUsername,
            "paddingLeft" toProperty paddingLeft,
        )
    }


}