package com.stella.free.web.page.todo

import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.core.todo.service.TodoService
import com.stella.free.global.util.logger
import com.stella.free.web.component.TodoViewComponent
import com.stella.free.web.component.pagination.PaginationViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.data.domain.Pageable

@ViewComponent
class TodoListViewComponent(
    private val todoService: TodoService,
    private val todoViewComponent: TodoViewComponent,
    private val paginationViewComponent: PaginationViewComponent,
) {

    val log = logger()


    fun render(pageable: Pageable, principal: UserPrincipal): ViewContext {

        val todos =
            todoService.findTodosByPage(pageable, principal)


        return ViewContext(
            "todos" toProperty todos,
            "todoViewComponent" toProperty todoViewComponent,
            "paginationViewComponent" toProperty paginationViewComponent,
        )
    }


}