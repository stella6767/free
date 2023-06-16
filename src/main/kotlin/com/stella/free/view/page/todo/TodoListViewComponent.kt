package com.stella.free.view.page.todo

import com.stella.free.config.security.UserPrincipal
import com.stella.free.repository.TodoRepository
import com.stella.free.util.logger
import com.stella.free.view.component.TodoViewComponent
import com.stella.free.view.component.pagination.PaginationViewComponent
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