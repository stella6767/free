package com.stella.free.view.component.post

import com.stella.free.entity.Post
import com.stella.free.util.logger
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