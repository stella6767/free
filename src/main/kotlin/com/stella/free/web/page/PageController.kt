package com.stella.free.web.page

import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.util.logger
import com.stella.free.web.component.blog.post.PostDetailViewComponent
import com.stella.free.web.component.blog.post.PostEditorViewComponent
import com.stella.free.web.page.layout.LayoutViewComponent
import com.stella.free.web.page.post.PostsViewComponent
import com.stella.free.web.page.resume.ResumeViewComponent
import com.stella.free.web.page.todo.TodoListViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class PageController(
    private val layoutViewComponent: LayoutViewComponent,
    private val resumeViewComponent: ResumeViewComponent,
    private val indexViewComponent: IndexViewComponent,
    private val todoListViewComponent: TodoListViewComponent,
    private val postsViewComponent: PostsViewComponent,
    private val postEditorViewComponent: PostEditorViewComponent,
    private val postDetailViewComponent: PostDetailViewComponent,
) {

    private val log = logger()

    @GetMapping("/")
    fun index(): ViewContext {

        return layoutViewComponent.render(indexViewComponent.render())
    }


    @GetMapping("/blog")
    fun homeBlog(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return layoutViewComponent.render(postsViewComponent.render(pageable))
    }


    @GetMapping("/resume")
    fun resume(): ViewContext {
        return layoutViewComponent.render(resumeViewComponent.render())
    }

    @GetMapping("/todos")
    fun todos(@PageableDefault(size = 10) pageable: Pageable,
              @AuthenticationPrincipal principal: UserPrincipal,
    ): ViewContext {

        return layoutViewComponent.render(todoListViewComponent.render(pageable, principal))
    }


    @GetMapping("/post/editor")
    fun postEditor(@PageableDefault(size = 16) pageable: Pageable): ViewContext {

        return layoutViewComponent.render(postEditorViewComponent.render())
    }


    @GetMapping("/post/{id}")
    fun getPostById(@PathVariable id: Long): ViewContext {

        return layoutViewComponent.render(postDetailViewComponent.render(id))
    }



}