package com.example.simpletodo.view.page.todo

import com.example.simpletodo.repository.TodoRepository
import com.example.simpletodo.util.logger
import com.example.simpletodo.view.component.TodoViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable

@ViewComponent
class TodoListViewComponent(
    private val todoRepository: TodoRepository,
    private val todoViewComponent: TodoViewComponent,
) {

    val log = logger()


    fun render(pageable: Pageable): ViewContext {

        val todos =
            todoRepository.findTodos(pageable)


        return ViewContext(
            "todos" toProperty todos,
            "todoViewComponent" toProperty todoViewComponent,
        )
    }


}