package com.stella.free.web.page.post

import com.stella.free.core.blog.service.PostService
import com.stella.free.web.component.blog.post.PostCardViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.util.StringUtils


@ViewComponent
class PostsViewComponent(
    private val postService: PostService,
    private val postCardViewComponent: PostCardViewComponent,
) {

    fun render(pageable: Pageable, keyword: String): ViewContext {

        val posts = if (StringUtils.hasLength(keyword)) {
            postService.findPostsByKeyword(keyword, pageable)
        }else postService.findPostsByPage(pageable)

        //println(keyword)

        return ViewContext(
            "keyword" toProperty keyword,
            "posts" toProperty posts,
            "postCardViewComponent" toProperty postCardViewComponent
        )
    }

}