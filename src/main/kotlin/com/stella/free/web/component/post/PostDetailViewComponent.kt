package com.stella.free.web.component.post

import com.stella.free.core.blog.service.PostService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class PostDetailViewComponent(
    private val postService: PostService,
) {

    fun render(id: Long): ViewContext {

        val postDetail =
            postService.findById(id).toDetailDto()

        return ViewContext(
            "post" toProperty postDetail
        )
    }

}