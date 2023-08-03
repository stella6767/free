package com.stella.free.core.openapi.dto

data class PublicApiCategoriesDto(
    val categories: List<String>,
    val count: Int
)


data class Entry(
    val API: String,
    val Auth: String,
    val Category: String,
    val Cors: String,
    val Description: String,
    val HTTPS: Boolean,
    val Link: String
)


data class EntryListDto(
    val count: Int,
    val entries: List<Entry>
)