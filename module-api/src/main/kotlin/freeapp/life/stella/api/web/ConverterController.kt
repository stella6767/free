package freeapp.life.stella.api.web

import freeapp.life.stella.api.service.HtmlToKotlinService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@RequestMapping("/converter")
@Controller
class ConverterController(
    private val htmlToKotlinService: HtmlToKotlinService
) {

    @GetMapping("")
    fun convertPage(): String {
        return "page/htmlToKotlin"
    }


    @ResponseBody
    @PostMapping("/html")
    fun convertHtmlToKotlin(
        input:String
    ): String {
        val convert = htmlToKotlinService.convert(input)
        return convert
    }


}