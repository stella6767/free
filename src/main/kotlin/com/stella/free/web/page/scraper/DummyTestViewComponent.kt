package com.stella.free.web.page.scraper

import com.stella.free.web.component.table.CommonTableViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class DummyTestViewComponent(
    private val commonTableViewComponent: CommonTableViewComponent
) {


    fun render(): ViewContext {

        return ViewContext(
            "commonTableViewComponent" toProperty commonTableViewComponent,
        )

    }

}