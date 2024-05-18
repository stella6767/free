package freeapp.life.todohtmx.view.page

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import kotlinx.html.stream.createHTML


fun renderComponent(div: DIV.() -> Unit): String {

    return createHTML().div {
        div()
    }
}

fun renderPageWithLayout(body: BODY.() -> Unit): String {
    return writePage {
        defaultHeader()
        body {
            body()
            defaultFooter()
        }
    }
}

inline fun writePage(crossinline block : HTML.() -> Unit): String {
    return createHTMLDocument().html {
        visit(block)
    }.serialize()
}


private fun HTML.defaultHeader() {
    head {
        script {
            src = "https://unpkg.com/htmx.org@1.9.10"
            integrity = "sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC"
            attributes["crossorigin"] = "anonymous"
        }
        script {
            src = "https://cdn.tailwindcss.com"
        }
        script {
            //defer
            src = "https://cdn.jsdelivr.net/npm/alpinejs@3.12.1/dist/cdn.min.js"
        }
        script {
            src = "https://cdn.tailwindcss.com"
        }

        link(rel = "stylesheet", href = "https://cdn.simplecss.org/simple.min.css")
        link(rel = "stylesheet", href = "/styles.css", type = "text/css")
        link {
            href = "https://cdn.jsdelivr.net/npm/daisyui@3.0.3/dist/full.css"
            rel = "stylesheet"
            type = "text/css"
        }
        meta {
            httpEquiv = "Content-Type"
            content = "text/html; charset=UTF-8"
        }
        meta { charset = "UTF-8" }
        meta(name = "author", content = "stella")
        meta(name = "keywords", content = arrayOf("Kotlin", "htmx").joinToString(","))
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
    }
}


fun BODY.defaultFooter(){
    footer("footer footer-center p-4 bg-base-300 text-base-content ") {
        id = "footer"
        div {
            p { +"""Created by Stella6767""" }
            p { +"""📞 alsrb9434@gmail.com""" }
            a {
                href = "https://github.com/stella6767"
                target = "_blank"
                style = "text-decoration: none;"
                +"""GitHub"""
            }
        }
    }
}





