package com.stella.free.core.scrap.api

import com.stella.free.core.scrap.dto.AsyncType
import com.stella.free.core.scrap.dto.VelogCrawlerReqDto
import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.core.scrap.service.VelogCrawler
import com.stella.free.web.component.table.CommonTableViewComponent
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
class ScrapController(
    private val dummyDataJenService: DummyDataJenService,
    private val commonTableViewComponent: CommonTableViewComponent,
    private val velogCrawler: VelogCrawler
) {


    @GetMapping("/velog")
    @ResponseBody
    fun velogApiTest(
        @Valid velogCrawlerReqDto: VelogCrawlerReqDto,
        response: HttpServletResponse
    ) {
        val headers = HttpHeaders()
        headers.add(CONTENT_DISPOSITION, "attachment; filename=files.zip")
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip")

        velogCrawler.parseAndDownloadAsZip(velogCrawlerReqDto.username, response.outputStream)
    }


    @GetMapping("/dummy/{size}")
    fun dummyGen(@PathVariable size:Int,
                 @RequestParam type: AsyncType
    ): String {

        dummyDataJenService.createDummyPersons(size, type)

        return ""
    }



//    @PostMapping("/dummy")
//    fun dummyPersonGen(@Valid dummyGenDto: DummyDataJenService.DummyGenDto): ViewContext {
//
//        val (type, size) = dummyGenDto
//
//        return commonTableViewComponent.render(dummyDataJenService.createDummyPersons(size, type))
//    }






}