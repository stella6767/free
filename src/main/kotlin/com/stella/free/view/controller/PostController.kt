package com.stella.free.view.controller

import com.stella.free.view.page.post.PostsViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PostController(
    private val postsViewComponent: PostsViewComponent,
) {


    @GetMapping("/posts")
    fun posts(): ViewContext {

        return postsViewComponent.render()
    }

}