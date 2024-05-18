package freeapp.life.todohtmx.controller


import freeapp.life.todohtmx.service.TodoService
import freeapp.life.todohtmx.view.component.todoComponent
import freeapp.life.todohtmx.view.page.renderPageWithLayout
import freeapp.life.todohtmx.view.component.todos
import freeapp.life.todohtmx.view.page.renderComponent
import jakarta.annotation.PostConstruct
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*


@RestController
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping("/todos")
    fun findTodos(
        @PageableDefault(size = 10) pageable: Pageable
    ): String {
        return renderPageWithLayout {
            todos(todoService.findTodosByPage(pageable))
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