package freeapp.life.stella.api.controller


import freeapp.life.stella.api.service.TodoService
import freeapp.life.stella.api.view.component.todoComponent
import freeapp.life.stella.api.view.page.renderPageWithLayout


import freeapp.life.stella.api.view.component.todosViewWithPage

import freeapp.life.stella.api.view.page.renderComponent
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*


@RestController
class TodoController(
    private val todoService: TodoService
) {


    @GetMapping("/page/todos")
    fun todoPage(
        @PageableDefault(size = 10) pageable: Pageable
    ): String {

        return renderPageWithLayout {
            todosViewWithPage(todoService.findTodosByPage(pageable))
        }
    }



    @GetMapping("/todos")
    fun findTodos(
        @PageableDefault(size = 10) pageable: Pageable
    ): String {

        return renderComponent {
            todosViewWithPage(todoService.findTodosByPage(pageable))
        }
    }

    @PutMapping("/todo/{id}")
    fun updateTodo(
        @PathVariable id: Long,
    ): String {

        val todo =
            todoService.updateStatusTodo(id)

        return renderComponent {
            todoComponent(todo)
        }
    }

    @DeleteMapping("/todo/{id}")
    fun deleteTodoById(@PathVariable id: Long): String {

        todoService.deleteTodoById(id)

        return ""
    }

    @PostMapping("/todo")
    fun addTodo(
        @RequestParam("newTodo") todo: String,
    ): String {

        val renderComponent = renderComponent {
            todoComponent(todoService.save(todo))
        }
        println(renderComponent)
        return renderComponent
    }


}