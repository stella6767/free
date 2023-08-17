package com.stella.free.web.component.table

import com.stella.free.core.scrap.service.DummyDataJenService
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class CommonTableViewComponent(

) {


    fun render(dummyPeople: List<DummyDataJenService.DummyPerson>): ViewContext {

        val fieldNames = dummyPeople.firstOrNull()?.javaClass?.declaredFields?.map {
            it.isAccessible = true
            it.name
        } ?: listOf()

        return ViewContext(
            "datas" toProperty dummyPeople,
            "fieldNames" toProperty fieldNames,
        )
    }

}