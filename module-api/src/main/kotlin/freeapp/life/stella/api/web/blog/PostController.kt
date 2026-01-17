package freeapp.life.stella.api.web.blog

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.PostService
import freeapp.life.stella.api.web.dto.PostSaveDto
import freeapp.life.stella.api.web.dto.PostUpdateDto
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

@Controller
class PostController(
    private val postService: PostService,
) {

    private val log = KotlinLogging.logger {  }

    @GetMapping("/blog")
    fun homeBlog(
        model: Model,
        @PageableDefault(size = 16, sort = ["createAt", "asc"]) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): String {

        val posts = postService.findPostCardDtos(keyword, pageable)

        model.addAttribute("keyword", keyword)
        model.addAttribute("posts", posts)
        model.addAttribute("currentSort", pageable.sort.first().direction.name)

        return "page/blog/post"
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

        return "page/blog/postDetail"
    }


    @GetMapping("/posts")
    fun posts(
        model: Model,
        @PageableDefault(size = 16, sort = ["createAt", "asc"]) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): String {

        val posts = postService.findPostCardDtos(keyword, pageable)

        model.addAttribute("posts", posts)
        model.addAttribute("keyword", keyword)
        model.addAttribute("currentSort", pageable.sort.first().direction.name)


        return "component/blog/postView"
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

        return "component/blog/postDetailView"
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

        return "component/blog/postByTagView"
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
        @RequestParam(required = false, defaultValue = "0") page: Int,
    ): String {

        log.debug { "??=>$page" }

        val post =
            postService.findPostDetailById(postId)

        model.addAttribute("post", post)
        model.addAttribute("page", page)

        return "component/blog/postEditorView"
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

        return  "page/blog/postByTag"
    }


}