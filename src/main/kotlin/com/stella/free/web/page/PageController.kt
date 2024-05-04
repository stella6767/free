package com.stella.free.web.page

import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.util.logger
import com.stella.free.web.component.blog.post.PostDetailViewComponent
import com.stella.free.web.component.blog.post.PostEditorViewComponent
import com.stella.free.web.page.chat.ChatViewComponent
import com.stella.free.web.page.layout.LayoutViewComponent
import com.stella.free.web.page.openapi.OpenApiListPageViewComponent
import com.stella.free.web.page.post.PostsByTagViewComponent
import com.stella.free.web.page.post.PostsViewComponent

import com.stella.free.web.page.aboutme.AboutMeViewComponent
import com.stella.free.web.page.scraper.TSDownloaderViewComponent
import com.stella.free.web.page.scraper.VelogCrawlerViewComponent
import com.stella.free.web.page.todo.TodoListViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PageController(
    private val layoutViewComponent: LayoutViewComponent,
    private val aboutMeViewComponent: AboutMeViewComponent,
    private val indexViewComponent: IndexViewComponent,
    private val todoListViewComponent: TodoListViewComponent,
    private val postsViewComponent: PostsViewComponent,
    private val postEditorViewComponent: PostEditorViewComponent,
    private val postDetailViewComponent: PostDetailViewComponent,
    private val openApiListPageViewComponent: OpenApiListPageViewComponent,
    private val chatViewComponent: ChatViewComponent,
    private val postsByTagViewComponent: PostsByTagViewComponent,
    private val velogCrawlerViewComponent: VelogCrawlerViewComponent,
    private val tsDownloaderViewComponent: TSDownloaderViewComponent,
    ) {

    private val log = logger()

    @GetMapping("/")
    fun index(): ViewContext {
        return layoutViewComponent.render(indexViewComponent.render())
    }


    @GetMapping("/blog")
    fun homeBlog(@PageableDefault(size = 16) pageable: Pageable,
                 @RequestParam(required = false, defaultValue = "") keyword: String): ViewContext {

        return layoutViewComponent.render(postsViewComponent.render(pageable, keyword))
    }


    @GetMapping("/blog/tag")
    fun blogByTagName(
        @PageableDefault(size = 16) pageable: Pageable,
        @RequestParam tagName: String
    ): ViewContext {

        return layoutViewComponent.render(postsByTagViewComponent.render(pageable, tagName))
    }



    @GetMapping("/resume")
    fun resume(): ViewContext {
        return layoutViewComponent.render(aboutMeViewComponent.render())
    }

    @GetMapping("/todos")
    fun todos(@PageableDefault(size = 10) pageable: Pageable,
              @AuthenticationPrincipal principal: UserPrincipal,
    ): ViewContext {

        return layoutViewComponent.render(todoListViewComponent.render(pageable, principal))
    }


    @GetMapping("/post/editor")
    fun postEditor(@PageableDefault(size = 16) pageable: Pageable,
                   @RequestParam(required = false, defaultValue = "0") postId:Long,
                   ): ViewContext {

        return layoutViewComponent.render(postEditorViewComponent.render(postId))
    }


    @GetMapping("/post/{id}")
    fun getPostById(@PathVariable id: Long): ViewContext {

        return layoutViewComponent.render(postDetailViewComponent.render(id))
    }


    @GetMapping("/publicApis")
    fun getOpenApiPage(): ViewContext {
        return layoutViewComponent.render(openApiListPageViewComponent.render())
    }

    @GetMapping("/chat")
    fun chatPage(): ViewContext {

        return layoutViewComponent.render(chatViewComponent.render())
    }



    @GetMapping("/velog/crawler")
    fun velogCrawlerViewComponentPage(): ViewContext {

        return layoutViewComponent.render(velogCrawlerViewComponent.render())
    }


    @GetMapping("/ts/downloader")
    fun tsDownloaderViewComponentPage(): ViewContext {

        return layoutViewComponent.render(tsDownloaderViewComponent.render())
    }



}