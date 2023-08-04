package com.stella.free.web.component.tabs

import com.stella.free.core.openapi.service.PublicApiService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class TabViewComponent(
) {

    fun render(category: String): ViewContext {

        return ViewContext(
            "category" toProperty category,
        )
    }

}