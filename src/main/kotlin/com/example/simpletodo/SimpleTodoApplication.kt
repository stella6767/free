package com.example.simpletodo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleTodoApplication

fun main(args: Array<String>) {
    runApplication<SimpleTodoApplication>(*args)
}
