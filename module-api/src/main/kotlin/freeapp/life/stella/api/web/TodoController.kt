package freeapp.life.stella.api.web


import freeapp.life.stella.api.service.TodoService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class TodoController(
    private val todoService: TodoService
) {


    @GetMapping("/page/todos")
    fun todoPage(
        model: Model,
        @PageableDefault(size = 10) pageable: Pageable
    ): String {
        val todos = todoService.findTodosByPage(pageable)
        model.addAttribute("todos", todos)
        return "page/todo"
    }



    @GetMapping("/todos")
    fun findTodos(
        model: Model,
        @PageableDefault(size = 10) pageable: Pageable
    ): String {

        val todos = todoService.findTodosByPage(pageable)

        model.addAttribute("todos", todos)

        return "component/todo/todosViewWithPage"
    }

    @PutMapping("/todo/{id}")
    fun updateTodo(
        model: Model,
        @PathVariable id: Long,
    ): String {

        val todo =
            todoService.updateStatusTodo(id)

        model.addAttribute("todo", todo)

        return "component/todo/todoComponent"
    }


    @ResponseBody
    @DeleteMapping("/todo/{id}")
    fun deleteTodoById(@PathVariable id: Long): String {
        todoService.deleteTodoById(id)
        return ""
    }

    @PostMapping("/todo")
    fun addTodo(
        model: Model,
        @RequestParam("newTodo") todo: String,
    ): String {

        val todo = todoService.save(todo)

        model.addAttribute("todo", todo)

        return "component/todo/todoComponent"
    }


}
