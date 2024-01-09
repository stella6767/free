package com.stella.free.web.component.blog


import com.stella.free.core.blog.dto.*
import com.stella.free.core.blog.service.CommentService
import com.stella.free.core.blog.service.PostService
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.util.logger
import com.stella.free.web.component.blog.comment.CommentCardViewComponent
import com.stella.free.web.component.blog.comment.CommentFormViewComponent
import com.stella.free.web.page.post.PostsByTagViewComponent
import com.stella.free.web.page.post.PostsViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
class BlogComponentController(
    private val postService: PostService,
    private val postsViewComponent: PostsViewComponent,
    private val postsByTagViewComponent: PostsByTagViewComponent,
    private val commentCardViewComponent: CommentCardViewComponent,
    private val commentFormViewComponent: CommentFormViewComponent,
    private val commentService: CommentService,
) {

    private val log = logger()

    @GetMapping("/posts")
    fun posts(
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): ViewContext {

        return postsViewComponent.render(pageable, keyword)
    }


    @GetMapping("/posts/tag")
    fun postsByTag(
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam tagName: String
    ): ViewContext {

        return postsByTagViewComponent.render(pageable,tagName)
    }



    @PostMapping("/post")
    @ResponseBody
    fun savePost(
        @RequestBody postSaveDto: PostSaveDto,
        @AuthenticationPrincipal principal: UserPrincipal
    ): String {

        postService.savePost(postSaveDto, principal)

        return "ok"
    }


    @PutMapping("/post")
    @ResponseBody
    fun updatePost(
        @RequestBody postUpdateDto: PostUpdateDto,
        @AuthenticationPrincipal principal: UserPrincipal
    ): String {

        postService.updatePost(postUpdateDto, principal)

        return "ok"
    }



    @DeleteMapping("/post/{id}")
    @ResponseBody
    fun deletePostById(
        @PathVariable id:Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): String {

        postService.deleteById(id, principal)

        return "ok"
    }


    @PostMapping("/post/file")
    @ResponseBody
    fun postFile(file: MultipartFile): String {
        return postService.savePostImg(file)
    }


    @DeleteMapping("/comment/{id}")
    @ResponseBody
    fun deleteCommentById(@PathVariable id:Long,
                          @RequestParam(required = false) password:String?
                          ): String {

        return commentService.deleteComment(id, password)
    }

    @PostMapping("/comment")
    fun saveComment(commentSaveDto: CommentSaveDto): ViewContext {

        val comment =
            commentService.saveComment(commentSaveDto)

        val childComment =
            commentService.findCommentsByBottomUp(comment.id)


        return commentCardViewComponent.render(childComment, commentCardViewComponent, commentSaveDto.paddingLeft)
    }


    @GetMapping("/comment/form/{postId}")
    fun getCommentForm(@PathVariable postId: Long): ViewContext {
        return commentFormViewComponent.render(postId)
    }


}