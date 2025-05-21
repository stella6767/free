package freeapp.life.stella.api.web

import freeapp.life.stella.api.service.HtmlToKotlinService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class ConverterController(
    private val htmlToKotlinService: HtmlToKotlinService
) {

    @GetMapping("/converter")
    fun convertPage(): String {
        return "page/htmlToKotlin"
    }


    @ResponseBody
    @PostMapping("/convert/html")
    fun convertHtmlToKotlin(
        input:String
    ): String {
        val convert = htmlToKotlinService.convert(input)
        return convert
    }



}
