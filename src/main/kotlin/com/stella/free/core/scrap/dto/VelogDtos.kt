package com.stella.free.core.scrap.dto

data class VelogTagDto(
    val description: Any,
    val id: String,
    val name: String,
    val posts_count: Int,
    val thumbnail: Any
)

data class VelogUserTagDto(
    val tags: List<VelogTagDto>,
    val posts_count: Int,
    val __typename: String,
)