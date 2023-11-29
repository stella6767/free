package com.stella.free.core.scrap.api

import com.stella.free.core.scrap.dto.VelogTagDto
import com.stella.free.core.scrap.dto.VelogUserTagDto
import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.core.scrap.service.StaticCrawler
import com.stella.free.web.component.table.CommonTableViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
class ScrapController(
    private val dummyDataJenService: DummyDataJenService,
    private val commonTableViewComponent: CommonTableViewComponent,
    private val staticCrawler: StaticCrawler
) {


    @GetMapping("/velog")
    @ResponseBody
    fun deleteTodoById(): MutableList<Any>? {

        return staticCrawler.getPosts("stella6767")
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