package com.stella.free.core.openapi.service

import com.stella.free.core.openapi.dto.PublicApiCategoriesDto
import com.stella.free.core.openapi.type.PublicApiEndPoints
import com.stella.free.global.util.logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono


@Service
class PublicApiService(
    private val publicApiClient:WebClient
) {

    private val log = logger()

    fun getAllCategory() {

        val endPoint = PublicApiEndPoints.CATEGORIES

        val publicApiCategoriesDto = publicApiClient
            .method(endPoint.method)
            .uri(endPoint.path)
            .accept(MediaType.ALL)
            .retrieve()
            .bodyToMono<PublicApiCategoriesDto>()
            .block() ?: throw RuntimeException("cant get public api category")

        println(publicApiCategoriesDto)

    }

}