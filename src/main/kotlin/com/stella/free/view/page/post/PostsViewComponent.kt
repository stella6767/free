package com.stella.free.view.page.post

import com.stella.free.service.PostService
import com.stella.free.view.component.post.PostCardViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable


@ViewComponent
class PostsViewComponent(
    private val postService: PostService,
    private val postCardViewComponent: PostCardViewComponent,
) {

    fun render(pageable: Pageable): ViewContext {

        val posts =
            postService.findPostsByPage(pageable)

        println(posts.size)

        return ViewContext(
            "posts" toProperty posts,
            "postCardViewComponent" toProperty postCardViewComponent
        )
    }

}