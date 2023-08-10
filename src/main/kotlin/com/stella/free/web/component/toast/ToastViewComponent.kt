package com.stella.free.web.component.toast

import de.tschuehly.spring.viewcomponent.core.ViewComponent

import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import jakarta.servlet.http.HttpServletResponse

@ViewComponent
class ToastViewComponent(
) {

    fun render(message: String, durationMs: Int) : ViewContext{

        return ViewContext(
            "message" toProperty message,
            "duration" toProperty durationMs
        )
    }


}