package com.stella.free.web.component.blog

import com.stella.free.core.blog.service.PostService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class PostDetailViewComponent(
    private val postService: PostService,
    private val commentSectionViewComponent: CommentSectionViewComponent,
) {

    fun render(id: Long): ViewContext {

        val postDetail =
            postService.findById(id)

        return ViewContext(
            "post" toProperty postDetail,
            "commentSectionViewComponent" toProperty commentSectionViewComponent
        )
    }

}