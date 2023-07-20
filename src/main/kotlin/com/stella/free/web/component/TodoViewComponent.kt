package com.stella.free.web.component

import com.stella.free.entity.Todo
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