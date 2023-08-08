package com.stella.free.web.component.blog.tag

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class TagViewComponent {

    fun render(tagName:String): ViewContext {

        return ViewContext(
            "tagName" toProperty tagName
        )
    }

}