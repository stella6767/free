package com.stella.free.web.page.layout

import com.stella.free.web.component.toast.ToastViewComponent
import com.stella.free.web.page.layout.footer.FooterViewComponent
import com.stella.free.web.page.layout.header.HeaderViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class LayoutViewComponent(
    private val headerViewComponent: HeaderViewComponent,
    private val footerViewComponent: FooterViewComponent,

    )  {

    fun render(nestedViewComponent: ViewContext): ViewContext {

        return ViewContext(
            "header" toProperty headerViewComponent.render(),
            "nestedView" toProperty nestedViewComponent,
            "footer" toProperty footerViewComponent.render(),
        )

    }

}