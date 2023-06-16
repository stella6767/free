package com.stella.free.view.component.toast

import de.tschuehly.spring.viewcomponent.core.ViewComponent

import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext

@ViewComponent
class ToastViewComponent {

    fun render(message: String, durationMs: Int) = ViewContext(
        "message" toProperty message,
        "duration" toProperty durationMs
    )
}