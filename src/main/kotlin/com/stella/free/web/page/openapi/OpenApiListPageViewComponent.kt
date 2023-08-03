package com.stella.free.web.page.openapi

import com.stella.free.web.component.table.TableViewComponent
import com.stella.free.web.component.tabs.TabViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class OpenApiListPageViewComponent(
    private val tableViewComponent: TableViewComponent,
    private val tabViewComponent: TabViewComponent,
) {


    fun render(): ViewContext {
        return ViewContext(
            "tableViewComponent" toProperty tableViewComponent,
            "tabViewComponent" toProperty tabViewComponent,
        )
    }

}