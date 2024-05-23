package freeapp.life.stella.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["freeapp.life.stella"])
class TodoHtmxApplication

fun main(args: Array<String>) {
    runApplication<TodoHtmxApplication>(*args)
}
