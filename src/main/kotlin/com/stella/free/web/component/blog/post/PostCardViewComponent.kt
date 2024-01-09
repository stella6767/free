package com.stella.free.web.component.blog.post

import com.stella.free.core.blog.dto.PostCardDto
import com.stella.free.core.blog.entity.Post
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.jsoup.Jsoup

@ViewComponent
class PostCardViewComponent(

) {

    private val log = logger()

    fun render(post: Post): ViewContext {

        val text =
            Jsoup.parse(post.content).text()

        return ViewContext(
            "post" toProperty PostCardDto.fromEntity(post, text)
        )
    }

}