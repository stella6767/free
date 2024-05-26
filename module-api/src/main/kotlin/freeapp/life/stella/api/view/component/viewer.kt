package freeapp.life.stella.api.view.component

import kotlinx.html.*

fun DIV.testView() {


    div {

        button(classes = "btn") {
            attributes["hx-trigger"] = "click"
            attributes["hx-get"] = "/alert"
            +"""htmx alert"""
        }
        button(classes = "btn") {
            onClick = "location.href='/blogasda'"
            +"""without htmx alert"""
        }

    }


}

fun DIV.indexView(value: String, isCenter: Boolean = true) {


    div("hero min-h-screen ") {
        attributes["style"] = "background-image: url(/img/background.jpg);"
        div("hero-overlay bg-opacity-30") {
            div {
                classes = setOf("container", "mx-auto", "px-4")
                div {
                    classes = setOf("flex", "justify-between", "mb-5")
                    htmlViewer(value, isCenter)
                }
            }
        }
    }
    script {
        src = "https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"
    }

}

fun DIV.htmlViewer(value: String, isCenter: Boolean = true) {

    link {
        attributes["rel"] = "stylesheet"
        href = "https://uicdn.toast.com/editor/latest/toastui-editor.min.css"
    }

    println("????")
    println(value)



    div {
        classes = setOf("toastui-editor-contents text-neutral-content")
        if (isCenter){
            classes += "text-center"
        }
        attributes["style"] = "overflow-wrap: break-word;"

        div {
            attributes["data-nodeid"] = "1"
            unsafe {
                raw(
                    value
                )
            }
        }
    }
}