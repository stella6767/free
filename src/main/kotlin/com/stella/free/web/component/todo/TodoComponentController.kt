package com.stella.free.web.component.todo

import com.stella.free.core.todo.service.TodoService
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class TodoComponentController(
    private val todoViewComponent: TodoViewComponent,
    private val todoService: TodoService,

) {

    private val log = logger()



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