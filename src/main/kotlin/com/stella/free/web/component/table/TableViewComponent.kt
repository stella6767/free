package com.stella.free.web.component.table

import com.stella.free.core.openapi.dto.Entry
import com.stella.free.core.openapi.service.PublicApiService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class TableViewComponent(
    private val publicApiService: PublicApiService
) {


    fun render(category:String = "Animals"): ViewContext {

        val entriesByCategory =
            publicApiService.getEntriesByCategory(category)

        val fieldNames =
            Entry::class.java.getDeclaredFields().map {
                it.isAccessible = true
                it.name
            }

        return ViewContext(
            "entries" toProperty  entriesByCategory.entries,
            "fieldNames" toProperty fieldNames
        )
    }

}