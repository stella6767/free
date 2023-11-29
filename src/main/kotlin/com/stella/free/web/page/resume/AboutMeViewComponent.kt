package com.stella.free.web.page.resume

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class AboutMeViewComponent(

) {
    fun render(): ViewContext {

        return ViewContext(
            "author" toProperty author,
        )
    }


    companion object {

        const val author = "Kang Min Kyu"

    }

}