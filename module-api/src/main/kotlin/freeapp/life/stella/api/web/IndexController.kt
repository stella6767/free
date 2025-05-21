package freeapp.life.stella.api.web

import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class IndexController(

) {

    @GetMapping("/")
    fun index(
        model: Model,
    ): String {

        model.addAttribute("value", ClassPathResource("static/README-korea.md").getMarkdownValueFormLocal())
        return "page/index"
    }

//    @GetMapping("/readme")
//    fun readme(
//        model: Model,
//    ): String {
//        val value = ClassPathResource("static/README-korea2.md").getMarkdownValueFormLocal()
//        model.addAttribute("value", value)
//        model.addAttribute("isCenter", false)
//        return "page/index"
//    }

    @GetMapping("/about/me")
    fun aboutMe(
        model: Model,
    ): String {

        model.addAttribute("author", "Kang Min Kyu")

        return "page/aboutMe"
    }


}
