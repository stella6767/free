package com.stella.free.web.component.blog.comment

import com.stella.free.core.account.entity.type.SignType
import com.stella.free.core.blog.dto.PostDetailDto
import de.tschuehly.spring.viewcomponent.core.component.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder


@ViewComponent
class CommentFormViewComponent(

) {

    fun render(postId: Long,
               idAncestor:Long = 0,
               paddingLeft:Int = 0,
    ): ViewContext {

        val authentication =
            SecurityContextHolder.getContext().authentication

        val isLogin =
            authentication !is AnonymousAuthenticationToken

        return ViewContext(
            "authentication" toProperty authentication,
            "isLogin" toProperty isLogin,
            "signType" toProperty SignType.GITHUB,
            "postId" toProperty postId,
            "idAncestor" toProperty idAncestor,
            "paddingLeft" toProperty paddingLeft
        )
    }

}