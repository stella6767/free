package com.stella.free.core.scrap.api

import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.core.scrap.service.WebScrapService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class ScrapController(
    private val scrapService: WebScrapService,
    private val dummyDataJenService: DummyDataJenService,
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

}