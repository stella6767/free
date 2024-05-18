package freeapp.life.todohtmx.service

import freeapp.life.todohtmx.repository.TodoRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service


@Service
class TodoService(
    private val todoRepository: TodoRepository
) {

    private val log = KotlinLogging.logger {  }




}