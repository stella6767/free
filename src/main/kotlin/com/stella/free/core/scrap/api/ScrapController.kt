package com.stella.free.core.scrap.api

import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.core.scrap.service.WebScrapService
import com.stella.free.web.component.table.CommonTableViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Controller
class ScrapController(
    private val scrapService: WebScrapService,
    private val dummyDataJenService: DummyDataJenService,
    private val commonTableViewComponent: CommonTableViewComponent,
) {


    @GetMapping("/url")
    fun scrapping(@RequestParam url:String){
        scrapService.getPageSource(url)
    }


    @GetMapping("/dummy/{size}")
    fun dummyGen(@PathVariable size:Int,
                 @RequestParam type:DummyDataJenService.AsyncType): String {

        dummyDataJenService.createDummyPersons(size, type)

        return ""
    }



    @PostMapping("/dummy")
    fun dummyPersonGen(@Valid dummyGenDto: DummyDataJenService.DummyGenDto): ViewContext {

        val (type, size) = dummyGenDto

        return commonTableViewComponent.render(dummyDataJenService.createDummyPersons(size, type))
    }

}