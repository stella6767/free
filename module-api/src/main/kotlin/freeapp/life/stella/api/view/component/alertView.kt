package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.view.page.path
import kotlinx.html.*

fun DIV.alertView() {

    div("alert alert-error") {
        role = "alert"
        svg("stroke-current shrink-0 h-6 w-6") {
            attributes["fill"] = "none"
            attributes["viewbox"] = "0 0 24 24"
            attributes["xmlns"] = "http://www.w3.org/2000/svg"
            path {
                attributes["stroke-linecap"] = "round"
                attributes["stroke-linejoin"] = "round"
                attributes["stroke-width"] = "2"
                attributes["d"] = "M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
            }
        }
        span { +"""Error! Task failed successfully.""" }
    }

}

fun DIV.progressView() {


    div {
        id = "loading-spinner"
        classes = setOf(
            "my-indicator",
            "absolute",
            "right-1/2",
            "bottom-1/2",
            "",
            "transform",
            "translate-x-1/2",
            "translate-y-1/2",
            "z-20"
        )
        div {
            classes = setOf(
                "border-t-transparent",
                "border-solid",
                "animate-spin",
                "",
                "rounded-full",
                "border-blue-400",
                "border-8",
                "h-64",
                "w-64"
            )
        }
    }

}


