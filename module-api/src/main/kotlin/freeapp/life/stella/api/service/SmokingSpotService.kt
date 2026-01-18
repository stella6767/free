package freeapp.life.stella.api.service

import freeapp.life.stella.api.web.dto.SmokingSpotMapDto
import freeapp.life.stella.storage.repository.SmokingSpotRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class SmokingSpotService(
    private val smokingSpotRepository: SmokingSpotRepository,
) {


    @Transactional(readOnly = true)
    fun findSmokingSpots(): List<SmokingSpotMapDto> {

        return smokingSpotRepository.findAll().map { SmokingSpotMapDto.fromEntity(it) }
    }


}


