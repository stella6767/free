package com.stella.free

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class FreeApplication

fun main(args: Array<String>) {
    runApplication<FreeApplication>(*args)
}
