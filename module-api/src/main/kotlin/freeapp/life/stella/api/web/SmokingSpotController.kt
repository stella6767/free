package freeapp.life.stella.api.web

import freeapp.life.stella.api.service.SmokingSpotService
import freeapp.life.stella.api.web.dto.SmokingSpotMapDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@RequestMapping("/smoking-spot")
@Controller
class SmokingSpotController(
    private val smokingSpotService: SmokingSpotService,
) {

    @GetMapping("")
    fun smokingSpotPage(
    ): String {
        return "page/smokingSpot"
    }

    @ResponseBody
    @GetMapping("/json")
    fun smokingSpots(): List<SmokingSpotMapDto> {

        return smokingSpotService.findSmokingSpots()
    }


}