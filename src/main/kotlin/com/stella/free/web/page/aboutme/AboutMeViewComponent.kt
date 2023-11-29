package com.stella.free.web.page.aboutme

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class AboutMeViewComponent(

) {

    private val author = "Kang Min Kyu"
    fun render(): ViewContext {

        return ViewContext(
            "author" toProperty author,
        )
    }

}