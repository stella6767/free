package com.stella.free.web.component.blog.comment

import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.entity.Comment
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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

        val authentication =
            SecurityContextHolder.getContext().authentication

        val userId = if (authentication !is AnonymousAuthenticationToken) {
            val userPrincipal = authentication.principal as UserPrincipal
            userPrincipal.user.id
        } else 0

        return ViewContext(
            "userId" toProperty userId,
            "comment" toProperty comment,
            "commentFormViewComponent" toProperty commentFormViewComponent,
            "commentCardViewComponent" toProperty commentCardViewComponent,
            "paddingLeft" toProperty paddingLeft,
        )
    }

}