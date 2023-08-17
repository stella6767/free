package com.stella.free.web.component.table

import com.stella.free.core.openapi.dto.Entry
import com.stella.free.core.scrap.service.DummyDataJenService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class CommonTableViewComponent(

) {


    fun render(createDummyPersons: List<DummyDataJenService.DummyPerson>): ViewContext {

        val fieldNames =
            DummyDataJenService.DummyPerson::class.java.getDeclaredFields().map {
                it.isAccessible = true
                it.name
            }

        return ViewContext(
            "datas" toProperty createDummyPersons,
            "fieldNames" toProperty fieldNames,
        )
    }

}