package freeapp.life.stella.api.controller

import freeapp.life.stella.api.dto.VelogCrawlerReqDto
import freeapp.life.stella.api.service.VelogCrawler
import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import freeapp.life.stella.api.view.component.aboutMeView
import freeapp.life.stella.api.view.component.velogCrawlerView
import freeapp.life.stella.api.view.page.renderPageWithLayout
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class VelogController(
    private val velogCrawler: VelogCrawler
) {

    private val log = KotlinLogging.logger { }


    @GetMapping("/velog/crawler")
    fun velogCrawlerView(): String {

        return renderPageWithLayout {
            velogCrawlerView(ClassPathResource("static/velog-README.md").getMarkdownValueFormLocal())
        }
    }


    @GetMapping("/velog")
    fun velogApiTest(
        @Valid velogCrawlerReqDto: VelogCrawlerReqDto,
        response: HttpServletResponse
    ) {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files.zip")
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip")
        velogCrawler.parseAndDownloadAsZip(velogCrawlerReqDto.username, response.outputStream)
    }

}