package com.stella.free.web.component

import com.stella.free.core.openapi.service.PublicApiService
import com.stella.free.web.component.table.PublicApiTableViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class PublicApiComponentController(
    private val tableViewComponent: PublicApiTableViewComponent,
) {

    @GetMapping("/table/{category}")
    fun getTables(@PathVariable category:String): ViewContext {

        return tableViewComponent.render(category)
    }

}