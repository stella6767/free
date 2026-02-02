package freeapp.life.stella.api.web

import freeapp.life.stella.api.service.PublicApiService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/public-api")
@Controller
class PublicApiController(
    private val publicApiService: PublicApiService
) {

    @GetMapping("")
    fun publicApiPage(
        model: Model,
        @PageableDefault(size = 10) pageable: Pageable
    ): String {
        val categories = publicApiService.getAllCategory()
        model.addAttribute("categories", categories)
        return "page/publicApi"
    }


    @GetMapping("/table/{category}")
    fun getTables(
        model: Model,
        @PathVariable category: String
    ): String {

        val entryListDto =
            publicApiService.getEntriesByCategory(category)

        model.addAttribute("entries", entryListDto.entries)
        model.addAttribute("fieldNames", entryListDto.filedNames)

        return "component/public-api/tableBodyView"
    }


}