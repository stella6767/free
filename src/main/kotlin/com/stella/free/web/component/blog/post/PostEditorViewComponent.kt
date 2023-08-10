package com.stella.free.web.component.blog.post

import com.stella.free.core.blog.service.PostService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import java.util.*


@ViewComponent
class PostEditorViewComponent(
    private val postService: PostService,
) {

    fun render(postId: Long): ViewContext {

        val post = if (postId != 0L) {
            postService.findPostById(postId)
        } else Optional.ofNullable(null)



        return ViewContext(
            "post" toProperty post,
            "tagNames" toProperty post.map { it.tagNames }.orElse("[]"),

        )
    }

}