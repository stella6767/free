package com.stella.free.web.page.openapi

import com.stella.free.core.openapi.service.PublicApiService
import com.stella.free.web.component.table.PublicApiTableViewComponent
import com.stella.free.web.component.tabs.TabViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class OpenApiListPageViewComponent(
    private val tableViewComponent: PublicApiTableViewComponent,
    private val tabViewComponent: TabViewComponent,
    private val publicApiService: PublicApiService, ) {


    fun render(): ViewContext {

        val categories =
            publicApiService.getAllCategory().categories

        return ViewContext(
            "tableViewComponent" toProperty tableViewComponent,
            "tabViewComponent" toProperty tabViewComponent,
            "categories" toProperty categories
        )
    }

}