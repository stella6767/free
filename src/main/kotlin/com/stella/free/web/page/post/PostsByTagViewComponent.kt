package com.stella.free.web.page.post

import com.stella.free.web.component.blog.post.PostCardViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class PostsByTagViewComponent(
    private val postCardViewComponent: PostCardViewComponent,
) {


    fun render(): ViewContext {

        return ViewContext(
            "postCardViewComponent" toProperty postCardViewComponent
        )
    }

}