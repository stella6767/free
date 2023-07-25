package com.stella.free.global.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {


    fun localDateTimeToString(dateTime: LocalDateTime, pattern: String): String {

        val dateTimeFormatter =
            DateTimeFormatter.ofPattern(pattern)

        return dateTimeFormatter.format(dateTime).toString()
    }


}