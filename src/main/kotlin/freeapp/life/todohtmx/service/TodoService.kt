package freeapp.life.todohtmx.service

import freeapp.life.todohtmx.entity.Todo
import freeapp.life.todohtmx.repository.TodoRepository
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class TodoService(
    private val todoRepository: TodoRepository
) {


    private val log = KotlinLogging.logger { }

    @PostConstruct
    fun init() {
        todoRepository.saveAll(createDummyTodos(20))
    }

    fun createDummyTodos(size: Int): MutableList<Todo> {

        val todos = mutableListOf<Todo>()

        for (i:Int in 1.. size) {
            todos.add(Todo(content = "todo$i"))
        }
        return todos
    }



    @Transactional(readOnly = true)
    fun findTodosByPage(pageable: Pageable): Page<Todo> {
        return todoRepository.findTodosWithPage(pageable)
    }

    @Transactional
    fun save(todo: String): Todo {
        val newTodo = Todo(content = todo)
        return todoRepository.save(newTodo)
    }

    @Transactional
    fun deleteTodoById(id: Long) {
        todoRepository.deleteById(id)
    }

    @Transactional
    fun updateStatusTodo(id: Long): Todo {

        val todo =
            todoRepository.findById(id).orElseThrow {
                throw EntityNotFoundException()
            }

        todo.status = !todo.status

        return todo
    }


}