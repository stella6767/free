package freeapp.life.stella.api.view.page


import freeapp.life.stella.api.view.component.loginModalView
import freeapp.life.stella.api.view.component.progressView
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import kotlinx.html.stream.appendHTML
import kotlinx.html.stream.createHTML
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.io.StringWriter


fun renderComponent(div: DIV.() -> Unit): String {
    return createHTML().div {
        div()
    }
}

fun renderPageWithLayout(bodyContent: DIV.() -> Unit): String {
    return writePage {
        defaultHeader()
        body {
            attributes["xmlns:hx-on"] = "http://www.w3.org/1999/xhtml"
            navbar()

            defaultBody {
                bodyContent()
            }

            defaultFooter()
        }
    }
}

inline fun writePage(crossinline block: HTML.() -> Unit): String {
    return createHTMLDocument().html {
        visit(block)
    }.serialize()
}


fun BODY.defaultBody(content: DIV.() -> Unit) {

    div {
        id = "content-body"
        classes = setOf("py-3")
        progressView()
        div {
            id = "toast"
        }
        content()
        loginModalView()
    }
}


private fun HTML.defaultHeader() {
    head {
        script {
            src = "https://unpkg.com/htmx.org@1.9.10"
            integrity = "sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC"
            attributes["crossorigin"] = "anonymous"
            defer = true
        }
        script {
            src = "https://unpkg.com/htmx.org@1.9.12/dist/ext/multi-swap.js"
            defer = true
        }
        script {
            src = "https://unpkg.com/htmx.org/dist/ext/loading-states.js"
            defer = true
        }
        script {
            src = "https://cdn.tailwindcss.com"
        }
        script {
            //defer
            src = "https://cdn.jsdelivr.net/npm/alpinejs@3.12.1/dist/cdn.min.js"
            defer = true
        }

        script {
            src = "/js/client.js"
            defer = true
        }


        link {
            href = "https://cdn.jsdelivr.net/npm/daisyui@3.0.3/dist/full.css"
            rel = "stylesheet"
            type = "text/css"
        }
        link {
            href = "/css/loading.css"
            rel = "stylesheet"
            type = "text/css"
        }

        meta {
            httpEquiv = "Content-Type"
            content = "text/html; charset=UTF-8"
        }
        meta { charset = "UTF-8" }
        meta(name = "author", content = "stella6767")
        meta(name = "keywords", content = arrayOf("Kotlin", "htmx").joinToString(","))
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")


        style {
            unsafe {
                raw(
                    """
                        @media (prefers-color-scheme:dark){
                            body{
                                color:white;
                                background:white;
                            }
                        }
                       """
                )
            }
        }

    }
}


fun BODY.defaultFooter() {

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




fun BODY.navbar() {

    div {
        classes = setOf("navbar", "bg-base-100", "sticky", "top-0", "z-30")
        div {
            classes = setOf("drawer", "navbar-start")
            input {
                id = "my-drawer"
                type = InputType.checkBox
                classes = setOf("drawer-toggle")
            }
            div {
                classes = setOf("drawer-content", "ml-5")
                label {
                    htmlFor = "my-drawer"
                    classes = setOf("drawer-button")
                    svg {
                        attributes["xmlns"] = "http://www.w3.org/2000/svg"
                        attributes["fill"] = "none"
                        attributes["viewbox"] = "0 0 24 24"
                        classes = setOf("inline-block", "w-5", "h-5", "stroke-current")


                        path {
                            attributes["stroke-linecap"] = "round"
                            attributes["stroke-linejoin"] = "round"
                            attributes["stroke-width"] = "2"
                            attributes["d"] = "M4 6h16M4 12h16M4 18h16"
                        }
                    }
                }
            }
            div {
                classes = setOf("drawer-side", "z-10")
                label {
                    htmlFor = "my-drawer"
                    classes = setOf("drawer-overlay")
                }
                ul {
                    classes = setOf("menu", "p-4", "w-80", "h-full", "bg-base-200", "text-base-content")
                    li {
                        a {
                            href = "/about/me"
                            +"about me"
                        }
                    }
                    li {
                        a {
                            href = "/page/todos"
                            +"todos"
                        }
                    }
                    li {
                        a {
                            href = "/blog"
                            +"blog"
                        }
                    }
                    li {
                        a {
                            href = "/publicApis"
                            +"public api list"
                        }
                    }
                    li {
                        a {
                            href = "/chat"
                            +"Chat Lab"
                        }
                    }
                    li {
                        a {
                            href = "/velog/crawler"
                            +"velog crawler"
                        }
                    }
                    li {
                        a {
                            href = "/converter"
                            +"html to kotlin"
                        }
                    }
                    li {
                        a {
                            href = "/test"
                            +"test"
                        }
                    }
                }
            }
        }
        div {
            classes = setOf("navbar-center")
            a {
                classes = setOf("btn", "btn-ghost", "normal-case", "text-xl", "mt-3")
                href = "/"
                +"Whatever"
            }
        }
        div {
            classes = setOf("navbar-end")
//            +"<%--        hx-get="/posts?page=0"--%>"
//            +"<%--        hx-target="#posts-container"--%>"
//            +"<%--        hx-swap="innerHTML"--%>"
            div {
                classes = setOf("max-w-md")
                form {
                    attributes["method"] = "get"
                    attributes["action"] = "/blog"
                    classes = setOf("relative", "mx-auto", "w-max")
                    input {
                        type = InputType.search
                        attributes["name"] = "keyword"
                        classes = setOf(
                            "peer",
                            "cursor-pointer",
                            "relative",
                            "z-10",
                            "h-12",
                            "w-12",
                            "rounded-full",
                            "border",
                            "bg-transparent",
                            "pl-12",
                            "outline-none",
                            "focus:w-full",
                            "focus:cursor-text",
                            "focus:border-lime-300",
                            "focus:pl-16",
                            "focus:pr-4"
                        )
                    }
                    svg {
                        attributes["xmlns"] = "http://www.w3.org/2000/svg"
                        classes = setOf(
                            "absolute",
                            "inset-y-0",
                            "my-auto",
                            "h-8",
                            "w-12",
                            "border-r",
                            "border-transparent",
                            "stroke-gray-500",
                            "px-3.5",
                            "peer-focus:border-lime-300",
                            "peer-focus:stroke-lime-500"
                        )
                        attributes["fill"] = "none"
                        attributes["viewbox"] = "0 0 24 24"
                        attributes["stroke"] = "currentColor"
                        attributes["stroke-width"] = "2"
                        path {
                            attributes["stroke-linecap"] = "round"
                            attributes["stroke-linejoin"] = "round"
                            attributes["d"] = "M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                        }
                    }
                }
            }
            ul {
                classes = setOf("menu", "menu-horizontal", "px-1", "mr-5")
                li {
                    details {
                        summary {
                            svg {
                                attributes["xmlns"] = "http://www.w3.org/2000/svg"
                                attributes["fill"] = "none"
                                attributes["viewbox"] = "0 0 24 24"
                                classes = setOf("inline-block", "w-5", "h-5", "stroke-current")
                                path {
                                    attributes["stroke-linecap"] = "round"
                                    attributes["stroke-linejoin"] = "round"
                                    attributes["stroke-width"] = "2"
                                    attributes["d"] =
                                        "M5 12h.01M12 12h.01M19 12h.01M6 12a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0z"
                                }
                            }
                        }
                        ul {
                            classes = setOf("p-2", "bg-base-100")

                            if(SecurityContextHolder.getContext().getAuthentication() is AnonymousAuthenticationToken){
                                li {
                                    a {
                                        attributes["onclick"] = "login_modal.showModal()"
                                        +"Login"
                                    }
                                }
                            }else{
                                li {
                                    a {
                                        href = "/logout"
                                        +"LogOut"
                                    }
                                }
                                li {
                                    a {
                                        attributes["hx-trigger"] = "click"
                                        attributes["hx-get"] = "/post/editor"
                                        attributes["hx-target"] = "#content-body"

                                        href = "/post/editor"
                                        +"새글쓰기"
                                    }
                                }
                            }

                            li {
                                a {
                                    href = "/readme"
                                    +"빌드 설명"
                                }
                            }

                        }
                    }
                }
            }

        }
    }




}



