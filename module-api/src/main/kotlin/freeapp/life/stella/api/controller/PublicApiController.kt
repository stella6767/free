package freeapp.life.stella.api.controller

import freeapp.life.stella.api.service.PublicApiService
import freeapp.life.stella.api.util.getMarkdownValueFormLocal
import freeapp.life.stella.api.view.component.indexView
import freeapp.life.stella.api.view.component.publicApiPageView
import freeapp.life.stella.api.view.component.tableBodyView
import freeapp.life.stella.api.view.page.renderComponent
import freeapp.life.stella.api.view.page.renderPageWithLayout
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class PublicApiController(
    private val publicApiService: PublicApiService
) {

    @GetMapping("/publicApis")
    fun publicApiPage(
        @PageableDefault(size = 10) pageable: Pageable
    ): String {

        return renderPageWithLayout {
            publicApiPageView(publicApiService.getAllCategory())
        }
    }


    @GetMapping("/table/{category}")
    fun getTables(@PathVariable category: String): String {
        return renderComponent {
            val entryListDto =
                publicApiService.getEntriesByCategory(category)
            tableBodyView(
                entryListDto.filedNames,
                entryListDto.entries
            )
        }
    }


}