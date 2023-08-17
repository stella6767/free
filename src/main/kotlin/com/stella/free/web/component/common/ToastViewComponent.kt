package com.stella.free.web.component.common

import de.tschuehly.spring.viewcomponent.core.ViewComponent

import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext

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