package freeapp.life.stella.api.view.component

import kotlinx.html.*


fun DIV.indexView(value: String) {

    link {
        attributes["rel"] = "stylesheet"
        href = "https://uicdn.toast.com/editor/latest/toastui-editor.min.css"
    }

    div("hero min-h-screen ") {
        attributes["style"] = "background-image: url(/img/background.jpg);"
        div("hero-overlay bg-opacity-30") {
            div {
                classes = setOf("container", "mx-auto", "px-4")
                div {
                    classes = setOf("flex", "justify-between", "mb-5")
                    htmlViewer(value)
                }
            }
        }
    }


    script {
        src = "https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"
    }

}

fun DIV.htmlViewer(value: String) {
    div {
        classes = setOf("toastui-editor-contents text-center text-neutral-content")
        attributes["style"] = "overflow-wrap: break-word;"
        div {
            attributes["data-nodeid"] = "1"

            unsafe {
                raw(
                    "$value"
                )
            }
        }
    }
}