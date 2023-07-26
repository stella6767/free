package com.stella.free.web.component.blog.comment

import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.entity.Comment
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class CommentCardViewComponent(
    private val commentFormViewComponent: CommentFormViewComponent,
) {

    private val log = logger()

    fun render(comment: CommentCardDto): ViewContext {

        return ViewContext(
            "comment" toProperty comment,
            "commentFormViewComponent" toProperty commentFormViewComponent
        )
    }

}