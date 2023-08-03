package com.stella.free.web.page.layout.header



import com.stella.free.web.component.auth.LoginModalViewComponent
import de.tschuehly.spring.viewcomponent.core.component.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class HeaderViewComponent(
    private val loginModalViewComponent: LoginModalViewComponent,
)  {
    fun render(): ViewContext {

        return ViewContext(
            "loginModalViewComponent" toProperty loginModalViewComponent
        )
    }


}