package com.stella.free.web.component.blog.comment

import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.entity.Comment
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.component.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import java.util.Map


@ViewComponent
class CommentCardViewComponent(
    private val commentFormViewComponent: CommentFormViewComponent,
) {

    private val log = logger()

    fun render(comment: CommentCardDto,
               commentCardViewComponent: CommentCardViewComponent,
               paddingLeft: Int = 0,
    ): ViewContext {


        return ViewContext(
            "comment" toProperty comment,
            "commentFormViewComponent" toProperty commentFormViewComponent,
            "commentCardViewComponent" toProperty commentCardViewComponent,
            "paddingLeft" toProperty paddingLeft,
        )
    }

}