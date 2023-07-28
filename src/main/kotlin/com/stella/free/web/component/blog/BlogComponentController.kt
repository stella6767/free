package com.stella.free.web.component.blog

import com.stella.free.core.blog.dto.CommentSaveDto
import com.stella.free.core.blog.dto.PostSaveDto
import com.stella.free.core.blog.service.CommentService
import com.stella.free.core.blog.service.PostService
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.util.logger
import com.stella.free.web.component.blog.comment.CommentCardViewComponent
import com.stella.free.web.component.blog.comment.CommentFormViewComponent
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
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

@Controller
class BlogComponentController(
    private val postService: PostService,
    private val postsViewComponent: PostsViewComponent,
    private val commentCardViewComponent: CommentCardViewComponent,
    private val commentFormViewComponent: CommentFormViewComponent,
    private val commentService: CommentService,
) {

    private val log = logger()

    @GetMapping("/posts")
    fun posts(@PageableDefault(size = 16) pageable: Pageable): ViewContext {
        return postsViewComponent.render(pageable)
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


    @PostMapping("/post/file")
    @ResponseBody
    fun postFile(file: MultipartFile): String {
        return postService.savePostImg(file)
    }



    @PostMapping("/comment")
    fun saveComment(commentSaveDto: CommentSaveDto): ViewContext {

        val commentCardDto =
            commentService.saveComment(commentSaveDto)

        return commentCardViewComponent.render(commentCardDto)
    }


    @GetMapping("/comment/form/{postId}")
    fun getCommentForm(@PathVariable postId: Long): ViewContext {
        return commentFormViewComponent.render(postId)
    }


}