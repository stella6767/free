package com.stella.free.web.component.todo

import com.stella.free.core.todo.entity.Todo

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class TodoViewComponent(

) {

    fun render(todo: Todo): ViewContext {

        return ViewContext(
            "todo" toProperty todo,
        )
    }

}