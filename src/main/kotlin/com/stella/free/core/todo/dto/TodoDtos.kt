package com.stella.free.core.todo.dto

import java.time.LocalDateTime


data class TodoResDto(
    val id:Long,
    val content:String,
    val status: Boolean,
    val createdAt: LocalDateTime,
)

data class StatusDto(
    val status: Boolean,
)