package freeapp.life.todohtmx.controller


import freeapp.life.todohtmx.service.TodoService
import freeapp.life.todohtmx.view.component.todoComponent
import freeapp.life.todohtmx.view.page.renderPageWithLayout
import freeapp.life.todohtmx.view.component.defaultBody

import freeapp.life.todohtmx.view.component.todosViewWithPage

import freeapp.life.todohtmx.view.page.renderComponent
import kotlinx.html.div
import kotlinx.html.id
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*


@RestController
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping("/")
    fun index(
        @PageableDefault(size = 10) pageable: Pageable
    ): String {

        return renderPageWithLayout {
            defaultBody{
                todosViewWithPage(todoService.findTodosByPage(pageable))
            }
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