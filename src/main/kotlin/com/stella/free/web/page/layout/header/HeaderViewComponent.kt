package com.stella.free.web.page.layout.header



import com.stella.free.web.component.auth.LoginModalViewComponent
import com.stella.free.web.component.common.ProgressViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class HeaderViewComponent(
    private val loginModalViewComponent: LoginModalViewComponent,
    private val progressViewComponent: ProgressViewComponent,
)  {
    fun render(): ViewContext {

        return ViewContext(
            "loginModalViewComponent" toProperty loginModalViewComponent,
            "progressViewComponent" toProperty progressViewComponent,
        )
    }


}