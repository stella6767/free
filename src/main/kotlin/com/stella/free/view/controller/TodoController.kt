package com.stella.free.view.controller

import com.stella.free.config.security.UserPrincipal
import com.stella.free.service.TodoService
import com.stella.free.util.logger
import com.stella.free.view.component.TodoViewComponent
import com.stella.free.view.page.layout.LayoutViewComponent
import com.stella.free.view.page.todo.TodoListViewComponent
import com.stella.free.view.page.IndexViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class TodoController(
    private val todoService: TodoService,

    private val layoutViewComponent: LayoutViewComponent,
    private val todoListViewComponent: TodoListViewComponent,
    private val indexViewComponent: IndexViewComponent,
    private val todoViewComponent: TodoViewComponent,
) {

    private val log = logger()

    @GetMapping("/")
    fun index(): ViewContext {

        return layoutViewComponent.render(indexViewComponent.render())
    }


    @GetMapping("/todos")
    fun todos(@PageableDefault(size = 10) pageable: Pageable,
              @AuthenticationPrincipal principal: UserPrincipal,
    ): ViewContext {

        return layoutViewComponent.render(todoListViewComponent.render(pageable, principal))
    }


    @PutMapping("/todo/{id}")
    fun updateTodo(
        @PathVariable id: Long,
    ): ViewContext {

        val todo =
            todoService.updateStatusTodo(id)

        return todoViewComponent.render(todo)
    }

    @DeleteMapping("/todo/{id}")
    @ResponseBody
    fun deleteTodoById(@PathVariable id: Long): String {

        todoService.deleteTodoById(id)

        return ""
    }

    @PostMapping("/todo")
    fun addTodo(
        @RequestParam("newTodo") todo: String,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ViewContext {


        return todoViewComponent.render(todoService.save(todo, principal))
    }


}