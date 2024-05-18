package freeapp.life.todohtmx.view.page


import kotlinx.html.*


fun BODY.todos(){

    div {
        id = "content-body"
        div("h-100 w-full flex items-center justify-center bg-teal-lightest font-sans") {
            div("bg-white rounded shadow p-6 m-4 w-full lg:w-3/4 lg:max-w-lg") {
                div("mb-4") {
                    h1("text-5xl font-bold") { +"""Todo List""" }
                    div("flex mt-4") {
                        input(classes = "shadow appearance-none border rounded w-full py-2 px-3 mr-4 text-grey-darker") {
                            attributes["autofocus"] = "autofocus"
                            id = "new-todo"
                            name = "newTodo"
                            placeholder = "To do..."
                            required = true
                            type = InputType.text
                        }
                        button(classes = "flex-no-shrink p-2 border-2 rounded text-teal border-teal hover:text-white hover:bg-teal") {
                            attributes["hx-include"] = "#new-todo"
                            attributes["hx-trigger"] = "click"
                            attributes["hx-post"] = "/todo"
                            attributes["hx-target"] = "#todo-list"
                            attributes["hx-swap"] = "afterbegin"
                            +"""Add"""
                        }
                    }
                }
                div {
                    id = "todo-list"
                    div {
                        id = "todo-container-9"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/9"
                                attributes["hx-target"] = "#todo-container-9"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/9"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-8"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/8"
                                attributes["hx-target"] = "#todo-container-8"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/8"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-7"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/7"
                                attributes["hx-target"] = "#todo-container-7"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/7"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-6"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/6"
                                attributes["hx-target"] = "#todo-container-6"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/6"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-5"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/5"
                                attributes["hx-target"] = "#todo-container-5"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/5"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-4"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasdasdasdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/4"
                                attributes["hx-target"] = "#todo-container-4"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/4"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-3"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest ") { +"""asdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/3"
                                attributes["hx-target"] = "#todo-container-3"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Done"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/3"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                    div {
                        id = "todo-container-1"
                        div("flex mb-4 items-center") {
                            id = "todo-box"
                            p("w-full text-grey-darkest line-through") { +"""asdasd""" }
                            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                                attributes["hx-trigger"] = "click"
                                attributes["hx-put"] = "/todo/1"
                                attributes["hx-target"] = "#todo-container-1"
                                attributes["hx-swap"] = "innerHTML"
                                +"""Yet"""
                            }
                            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                                attributes["hx-confirm"] = "Are you sure?"
                                attributes["hx-swap"] = "outerHTML swap:1s"
                                attributes["hx-trigger"] = "click"
                                attributes["hx-delete"] = "/todo/1"
                                attributes["hx-target"] = "closest div"
                                +"""Remove"""
                            }
                        }
                    }
                }
            }
        }
        div("flex justify-center") {
            div("join ") {
                button(classes = "join-item btn") {
                    onClick = "location.href='/todos?page=0'"
                    +"""First"""
                }
                button(classes = "join-item btn") {
                    disabled = true
                    +"""&lt;"""
                }
                input(classes = "join-item btn btn-square") {
                    type = InputType.radio
                    name = "options"
                    attributes["aria-label"] = "1"
                    //onclick = "window.location.href='/todos?page=0'"
                    checked = true
                }
                button(classes = "join-item btn") {
                    disabled = true
                    +"""&gt;"""
                }
                button(classes = "join-item btn") {
                    //onclick = "location.href='/todos?page=0'"
                    +"""Last"""
                }
            }
        }
        br {
        }
    }



}
