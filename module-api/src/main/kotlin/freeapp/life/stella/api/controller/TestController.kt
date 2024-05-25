package freeapp.life.stella.api.controller

import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import freeapp.life.stella.api.view.component.indexView
import freeapp.life.stella.api.view.component.testView
import freeapp.life.stella.api.view.page.renderPageWithLayout
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(

) {

    private val log = KotlinLogging.logger { }

    @GetMapping("/test")
    fun testPage(

    ): String {

        return renderPageWithLayout {
            testView()
        }
    }


    @GetMapping("/alert")
    fun alert(
    ): String {

        throw IllegalArgumentException("alert Test")
    }


}