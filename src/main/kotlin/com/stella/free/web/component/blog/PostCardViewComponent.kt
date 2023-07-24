package com.stella.free.web.component.blog

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
            "post" toProperty post.toCardDto(text)
        )
    }

}