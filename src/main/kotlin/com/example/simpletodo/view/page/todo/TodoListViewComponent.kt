package com.example.simpletodo.view.page.todo

import com.example.simpletodo.config.security.UserPrincipal
import com.example.simpletodo.repository.TodoRepository
import com.example.simpletodo.util.logger
import com.example.simpletodo.view.component.TodoViewComponent
import com.example.simpletodo.view.component.pagination.PaginationViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable

@ViewComponent
class TodoListViewComponent(
    private val todoRepository: TodoRepository,
    private val todoViewComponent: TodoViewComponent,
    private val paginationViewComponent: PaginationViewComponent,
) {

    val log = logger()


    fun render(pageable: Pageable, principal: UserPrincipal): ViewContext {

        val todos =
            todoRepository.findTodos(pageable, principal.user)


        return ViewContext(
            "todos" toProperty todos,
            "todoViewComponent" toProperty todoViewComponent,
            "paginationViewComponent" toProperty paginationViewComponent,
        )
    }


}