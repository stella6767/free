package com.stella.free.web.controller

import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.dto.PostSaveDto
import com.stella.free.core.blog.service.PostService
import com.stella.free.global.util.logger
import com.stella.free.web.component.post.PostDetailViewComponent
import com.stella.free.web.component.post.PostEditorViewComponent
import com.stella.free.web.page.layout.LayoutViewComponent
import com.stella.free.web.page.post.PostsViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile


@Controller
@RequestMapping("/blog")
class BlogController(
    private val postService: PostService,
    private val layoutViewComponent: LayoutViewComponent,
    private val postsViewComponent: PostsViewComponent,
    private val postEditorViewComponent: PostEditorViewComponent,
    private val postDetailViewComponent: PostDetailViewComponent,
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


    @GetMapping("/post/{id}")
    fun getPostById(@PathVariable id: Long): ViewContext {

        return layoutViewComponent.render(postDetailViewComponent.render(id))
    }



    @GetMapping("/post/editor")
    fun postEditor(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return layoutViewComponent.render(postEditorViewComponent.render())
    }


    @PostMapping("/post")
    @ResponseBody
    fun savePost(
        @RequestBody postSaveDto: PostSaveDto,
        @AuthenticationPrincipal principal: UserPrincipal?
    ): String {

        postService.savePost(postSaveDto, principal)

        return "ok"
    }


    @PostMapping("/file")
    @ResponseBody
    fun postFile(file: MultipartFile): String {

        println(file.originalFilename)
        return ""
    }


}