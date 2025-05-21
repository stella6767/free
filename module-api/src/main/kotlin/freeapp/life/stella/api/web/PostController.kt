package freeapp.life.stella.api.web

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.PostService

import freeapp.life.stella.api.web.dto.PostSaveDto
import freeapp.life.stella.api.web.dto.PostUpdateDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@Controller
class PostController(
    private val postService: PostService,
) {


    @GetMapping("/blog")
    fun homeBlog(
        model: Model,
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): String {

        val posts = postService.findPostCardDtos(keyword, pageable)

        model.addAttribute("keyword", keyword)
        model.addAttribute("posts", posts)

        return "page/post"
    }


    @GetMapping("/page/post/{id}")
    fun postDetailPage(
        model: Model,
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable id: Long,
    ): String {


        val post =
            postService.findPostDetailById(id) ?: throw EntityNotFoundException()

        model.addAttribute("post", post)
        model.addAttribute("userId", userPrincipal?.user?.id)

        return "page/postDetail"
    }


    @GetMapping("/posts")
    fun posts(
        model: Model,
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): String {

        model.addAttribute("posts", postService.findPostCardDtos(keyword, pageable))
        model.addAttribute("keyword", keyword)

        return "component/post/postView"
    }


    @GetMapping("/post/{id}")
    fun postDetail(
        model: Model,
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable id: Long,
    ): String {
        val post =
            postService.findPostDetailById(id) ?: throw EntityNotFoundException()

        model.addAttribute("userId", userPrincipal?.user?.id)
        model.addAttribute("post", post)

        return "component/post/postDetailView"
    }


    @ResponseBody
    @GetMapping("/post/content/{id}")
    fun findPostDetailContent(
        model: Model,
        @PathVariable id: Long,
    ): String {
        val post =
            postService.findPostDetailById(id) ?: throw EntityNotFoundException()

        return post.content
    }


    @ResponseBody
    @PostMapping("/post")
    fun savePost(
        model: Model,
        @RequestBody postSaveDto: PostSaveDto,
        @AuthenticationPrincipal principal: UserPrincipal
    ): Long {

        return postService.savePost(postSaveDto, principal)
    }


    @ResponseBody
    @PutMapping("/post")
    fun updatePost(
        model: Model,
        @RequestBody postUpdateDto: PostUpdateDto,
        @AuthenticationPrincipal principal: UserPrincipal
    ): Long {

        return postService.updatePost(postUpdateDto, principal)
    }


    @ResponseBody
    @DeleteMapping("/post/{id}")
    fun deletePostById(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): String {
        postService.deleteById(id, principal)
        return "ok"
    }


    @GetMapping("/posts/tag")
    fun postsByTag(
        model: Model,
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam tagName: String
    ): String {

        val posts =
            postService.findPostsByTagName(tagName, pageable)

        model.addAttribute("posts", posts)
        model.addAttribute("tagName", tagName)

        return "component/post/postByTagView"
    }


    @ResponseBody
    @PostMapping("/post/file")
    fun postFile(file: MultipartFile): String {
        return postService.savePostImg(file)
    }


    @GetMapping("/post/editor")
    fun postEditor(
        model: Model,
        @RequestParam(required = false, defaultValue = "0") postId: Long,
    ): String {

        val post =
            postService.findPostDetailById(postId)
        model.addAttribute("post", post)

        return "component/post/postEditorView"
    }


    @GetMapping("/blog/tag")
    fun blogByTagName(
        model: Model,
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam tagName: String
    ): String {

        val posts =
            postService.findPostsByTagName(tagName, pageable)

        model.addAttribute("posts", posts)
        model.addAttribute("tagName", tagName)

        return  "page/postByTag"
    }


}
