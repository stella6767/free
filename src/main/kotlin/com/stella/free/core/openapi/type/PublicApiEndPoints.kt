package com.stella.free.core.openapi.type

import org.springframework.http.HttpMethod

enum class PublicApiEndPoints(
    val path: String,
    val method: HttpMethod,
) {

    CATEGORIES("categories", HttpMethod.GET),
    ENTRIES("entries", HttpMethod.GET),


}