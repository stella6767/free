package freeapp.life.stella.api.controller

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.dto.PostSaveDto
import freeapp.life.stella.api.dto.PostUpdateDto
import freeapp.life.stella.api.service.PostService
import freeapp.life.stella.api.view.component.postByTagView
import freeapp.life.stella.api.view.component.postDetailView
import freeapp.life.stella.api.view.component.postEditorView
import freeapp.life.stella.api.view.component.postView
import freeapp.life.stella.api.view.page.renderComponent
import freeapp.life.stella.api.view.page.renderPageWithLayout
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
class PostController(
    private val postService: PostService,

    ) {

    private val log = KotlinLogging.logger { }


    @GetMapping("/blog")
    fun homeBlog(
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): String {

        return renderPageWithLayout {
            postView(keyword, postService.findPostCardDtos(keyword, pageable))
        }
    }

    @GetMapping("/page/post/{id}")
    fun postDetailPage(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable id: Long,
    ): String {

        return renderPageWithLayout {
            val post =
                postService.findPostDetailById(id) ?: throw EntityNotFoundException()

            postDetailView(userPrincipal?.user?.id, post)
        }
    }


    @GetMapping("/posts")
    fun posts(
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): String {

        return renderComponent {
            postView(keyword, postService.findPostCardDtos(keyword, pageable))
        }
    }


    @GetMapping("/post/{id}")
    fun postDetail(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable id: Long,
    ): String {
        return renderComponent {
            val post =
                postService.findPostDetailById(id) ?: throw EntityNotFoundException()
            postDetailView(userPrincipal?.user?.id, post)
        }
    }


    @PostMapping("/post")
    fun savePost(
        @RequestBody postSaveDto: PostSaveDto,
        @AuthenticationPrincipal principal: UserPrincipal
    ): Long {

        return postService.savePost(postSaveDto, principal)
    }


    @PutMapping("/post")
    fun updatePost(
        @RequestBody postUpdateDto: PostUpdateDto,
        @AuthenticationPrincipal principal: UserPrincipal
    ): Long {

        return postService.updatePost(postUpdateDto, principal)
    }


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
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam tagName: String
    ): String {

        val posts =
            postService.findPostsByTagName(tagName, pageable)

        return renderComponent {
            postByTagView(tagName, posts)
        }
    }


    @PostMapping("/post/file")
    fun postFile(file: MultipartFile): String {
        return postService.savePostImg(file)
    }


    @GetMapping("/post/editor")
    fun postEditor(
        @RequestParam(required = false, defaultValue = "0") postId: Long,
    ): String {

        val post =
            postService.findPostDetailById(postId)

        return renderComponent {
            postEditorView(post)
        }
    }


    @GetMapping("/blog/tag")
    fun blogByTagName(
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam tagName: String
    ): String {

        return renderPageWithLayout {
            val posts =
                postService.findPostsByTagName(tagName, pageable)
            postByTagView(tagName, posts)
        }
    }


}