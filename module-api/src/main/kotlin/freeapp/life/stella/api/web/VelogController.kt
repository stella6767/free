package freeapp.life.stella.api.web


import freeapp.life.stella.api.service.VelogCrawler
import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import freeapp.life.stella.api.web.dto.VelogCrawlerReqDto
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class VelogController(
    private val velogCrawler: VelogCrawler
) {


    @GetMapping("/velog/crawler")
    fun velogCrawlerView(
        model: Model,
    ): String {

        model.addAttribute("htmlContent", ClassPathResource("static/doc/velog-README.md").getMarkdownValueFormLocal())

        return "page/velog"
    }


    @ResponseBody
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
