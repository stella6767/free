package freeapp.life.stella.api.controller

import freeapp.life.stella.api.service.TodoService
import freeapp.life.stella.api.view.component.todoComponent
import freeapp.life.stella.api.view.page.renderComponent
import freeapp.life.stella.storage.entity.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*


@RequestMapping("/json")
@RestController
class TodoControllerWithJson(
    private val todoService: TodoService

) {

    data class CmResDto<T>(
        val msg: String,
        val data: T,
    )

    @GetMapping("/todos")
    fun findTodos(
        @PageableDefault(size = 10) pageable: Pageable
    ): CmResDto<Page<Todo>> {

        return CmResDto(
            msg = "find todos",
            data = todoService.findTodosByPage(pageable)
        )
    }


    @PutMapping("/todo/{id}")
    fun updateTodo(
        @PathVariable id: Long,
    ): CmResDto<Todo> {

        val todo =
            todoService.updateStatusTodo(id)

        return CmResDto(
            msg = "update todo",
            data = todo
        )
    }

    @DeleteMapping("/todo/{id}")
    fun deleteTodoById(@PathVariable id: Long): CmResDto<Unit> {

        return CmResDto(
            msg = "delete todo",
            data = todoService.deleteTodoById(id)
        )
    }

    @PostMapping("/todo")
    fun addTodo(
        @RequestParam("newTodo") todo: String,
    ): CmResDto<Todo> {

        return CmResDto(
            msg = "add todo",
            data = todoService.save(todo)
        )
    }

}