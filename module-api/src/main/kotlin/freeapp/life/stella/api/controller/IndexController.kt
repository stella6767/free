package freeapp.life.stella.api.controller

import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import freeapp.life.stella.api.view.component.aboutMeView
import freeapp.life.stella.api.view.component.indexView
import freeapp.life.stella.api.view.page.renderPageWithLayout
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class IndexController(

) {

    private val log = KotlinLogging.logger {  }

    @GetMapping("/")
    fun index(
        @PageableDefault(size = 10) pageable: Pageable
    ): String {

        return renderPageWithLayout {
            indexView(ClassPathResource("static/README-korea.md").getMarkdownValueFormLocal())
        }
    }


    @GetMapping("/about/me")
    fun aboutMe(): String {

        return renderPageWithLayout {
            aboutMeView("Kang Min Kyu")
        }
    }



}