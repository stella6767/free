package freeapp.life.todohtmx.controller


import freeapp.life.todohtmx.view.component.renderPageWithLayout
import freeapp.life.todohtmx.view.page.todos
import kotlinx.html.div
import kotlinx.html.h1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TodoController {


    @GetMapping("/")
    fun index(): String {

        return renderPageWithLayout {
            todos()
        }
    }





}