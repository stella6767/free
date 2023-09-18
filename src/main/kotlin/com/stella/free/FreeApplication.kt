package com.stella.free

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class FreeApplication

//todo 댓글 구조 변경..

fun main(args: Array<String>) {
    runApplication<FreeApplication>(*args)
}
