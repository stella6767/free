package com.stella.free.view.controller

import com.stella.free.util.logger
import com.stella.free.view.component.post.PostEditorViewComponent
import com.stella.free.view.page.layout.LayoutViewComponent
import com.stella.free.view.page.post.PostsViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile


@Controller
@RequestMapping("/blog")
class BlogController(
    private val layoutViewComponent: LayoutViewComponent,
    private val postsViewComponent: PostsViewComponent,
    private val postEditorViewComponent: PostEditorViewComponent,
) {

    private val log = logger()

    @GetMapping("/home")
    fun homeBlog(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return layoutViewComponent.render(postsViewComponent.render(pageable))
    }


    @GetMapping("/posts")
    fun posts(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return postsViewComponent.render(pageable)
    }


    @GetMapping("/post/editor")
    fun postEditor(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return layoutViewComponent.render(postEditorViewComponent.render())
    }



    @PostMapping("/file")
    @ResponseBody
    fun postFile(file: MultipartFile): String {

        println(file.originalFilename)
        return ""
    }


}