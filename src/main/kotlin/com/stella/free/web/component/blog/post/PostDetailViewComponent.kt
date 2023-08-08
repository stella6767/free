package com.stella.free.web.component.blog.post

import com.stella.free.core.blog.service.PostService
import com.stella.free.web.component.blog.comment.CommentCardViewComponent
import com.stella.free.web.component.blog.comment.CommentSectionViewComponent
import com.stella.free.web.component.blog.tag.TagViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class PostDetailViewComponent(
    private val postService: PostService,
    private val commentSectionViewComponent: CommentSectionViewComponent,
    private val tagViewComponent: TagViewComponent,
) {

    fun render(id: Long): ViewContext {

        val postDetail =
            postService.findById(id)

        val postTags =
            postService.findTagsByPostId(id)

        return ViewContext(
            "post" toProperty postDetail,
            "commentSectionViewComponent" toProperty commentSectionViewComponent,
            "postTags" toProperty postTags,
            "tagViewComponent" toProperty tagViewComponent,
        )
    }

}