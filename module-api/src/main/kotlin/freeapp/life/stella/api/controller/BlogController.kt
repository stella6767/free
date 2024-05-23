package freeapp.life.stella.api.controller

import freeapp.life.stella.api.service.PostService
import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import freeapp.life.stella.api.view.component.indexView
import freeapp.life.stella.api.view.component.postView
import freeapp.life.stella.api.view.page.renderPageWithLayout
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class BlogController(
    private val postService: PostService,

) {

    private val log = KotlinLogging.logger {  }


    @GetMapping("/blog")
    fun homeBlog(@PageableDefault(size = 16) pageable: Pageable,
                 @RequestParam(required = false, defaultValue = "") keyword: String): String {

        val posts = if (StringUtils.hasLength(keyword)) {
            postService.findPostsByKeyword(keyword, pageable)
        }else postService.findPostsByPage(pageable)

        return renderPageWithLayout {
            postView(keyword, posts)
        }
    }




}