package com.stella.free.view.controller

import com.stella.free.util.logger
import com.stella.free.view.page.layout.LayoutViewComponent
import com.stella.free.view.page.post.PostsViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/blog")
class BlogController(
    private val layoutViewComponent: LayoutViewComponent,
    private val postsViewComponent: PostsViewComponent,
) {

    private val log = logger()

    @GetMapping("/home/posts")
    fun homeBlog(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return layoutViewComponent.render(postsViewComponent.render(pageable))
    }


    @GetMapping("/posts")
    fun posts(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return postsViewComponent.render(pageable)
    }


}