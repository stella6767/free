package com.stella.free.core.todo.service

import com.stella.free.core.todo.entity.Todo
import com.stella.free.core.todo.repo.TodoRepository
import com.stella.free.global.config.security.UserPrincipal

import com.stella.free.global.util.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TodoService(
    private val todoRepository: TodoRepository,
) {

    val logger = logger()


    fun save(todo: String, principal: UserPrincipal): Todo {

        val newTodo = Todo(content = todo, user = principal.user)

        return todoRepository.save(newTodo)
    }


    fun findTodosByPage(pageable: Pageable, principal: UserPrincipal): Page<Todo> {
        return todoRepository.findTodos(pageable, principal.user)
    }


    fun deleteTodoById(id: Long){

        todoRepository.deleteById(id)
    }


    fun updateStatusTodo(id: Long): Todo {

        val todo =
            todoRepository.findById(id).orElseThrow()

        todo.status = !todo.status

        return todo
    }



}