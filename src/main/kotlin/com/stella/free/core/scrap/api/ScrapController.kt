package com.stella.free.core.scrap.api

import com.stella.free.core.scrap.service.WebScrapService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class ScrapController(
    private val scrapService: WebScrapService,
) {


    @GetMapping("/url")
    fun scrapping(@RequestParam url:String){

        scrapService.getPageSource(url)

    }

}