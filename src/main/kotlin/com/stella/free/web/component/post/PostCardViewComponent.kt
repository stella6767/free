package com.stella.free.web.component.post

import com.stella.free.core.blog.entity.Post
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext

@ViewComponent
class PostCardViewComponent(

) {

    private val log = logger()


    fun render(post: Post): ViewContext {

        return ViewContext(
            "post" toProperty post

        )
    }

}