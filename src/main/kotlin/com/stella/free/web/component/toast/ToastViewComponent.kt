package com.stella.free.web.component.toast

import de.tschuehly.spring.viewcomponent.core.ViewComponent

import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import jakarta.servlet.http.HttpServletResponse

@ViewComponent
class ToastViewComponent(
    private val response: HttpServletResponse
) {

    fun render(message: String, durationMs: Int) : ViewContext{

        //response.addHeader("HX-Retarget", "#toast")

        return ViewContext(
            "message" toProperty message,
            "duration" toProperty durationMs
        )
    }


}