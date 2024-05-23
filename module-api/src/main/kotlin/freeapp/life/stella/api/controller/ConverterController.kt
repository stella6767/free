package freeapp.life.stella.api.controller

import freeapp.life.stella.api.service.HtmlToKotlinService
import freeapp.life.stella.api.view.component.chatView
import freeapp.life.stella.api.view.component.htmlToKotlinConverter
import freeapp.life.stella.api.view.page.renderPageWithLayout
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ConverterController(
    private val htmlToKotlinService: HtmlToKotlinService
) {

    private val log = KotlinLogging.logger {  }

    @GetMapping("/converter")
    fun convertPage(): String {

        return renderPageWithLayout {
            htmlToKotlinConverter()
        }
    }


    @PostMapping("/convert/html")
    fun convertHtmlToKotlin(
        input:String
    ): String {

        val convert = htmlToKotlinService.convert(input)

        println(convert)

        return convert
    }


}