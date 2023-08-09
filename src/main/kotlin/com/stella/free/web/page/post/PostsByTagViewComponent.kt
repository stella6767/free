package com.stella.free.web.page.post

import com.stella.free.core.blog.service.PostService
import com.stella.free.web.component.blog.post.PostCardViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable


@ViewComponent
class PostsByTagViewComponent(
    private val postCardViewComponent: PostCardViewComponent,
    private val postService: PostService,
) {

    fun render(pageable: Pageable, tagName:String): ViewContext {

        val posts =
            postService.findPostsByTagName(tagName, pageable)

        return ViewContext(
            "postCardViewComponent" toProperty postCardViewComponent,
            "posts" toProperty posts,
            "tagName" toProperty tagName,
        )
    }

}