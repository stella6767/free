package freeapp.life.stella.api.web.dto

import freeapp.life.stella.storage.entity.SmokingSpot

data class SmokingSpotMapDto(
    val id: Long,
    val name: String,
    val address: String
) {

    companion object {

        fun fromEntity(smokingSpot: SmokingSpot): SmokingSpotMapDto {

            return SmokingSpotMapDto(
                smokingSpot.id,
                smokingSpot.name,
                smokingSpot.address
            )
        }
    }

}