package com.stella.free.view.controller

import com.stella.free.util.logger
import com.stella.free.view.page.layout.LayoutViewComponent
import com.stella.free.view.page.post.PostsViewComponent
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class BlogController(
    private val layoutViewComponent: LayoutViewComponent,
    private val postsViewComponent: PostsViewComponent,
) {

    private val log = logger()


    @GetMapping("/posts")
    fun posts(){

        layoutViewComponent.render(postsViewComponent.render())

    }



}