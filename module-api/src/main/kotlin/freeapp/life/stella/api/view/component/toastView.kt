package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.view.page.path
import kotlinx.html.*

fun DIV.alertView(msg:String, duration:Int = 5000) {

    div("alert alert-error fixed bottom-10 z-40 left-1/2 -translate-x-1/2") {
        id = "alert-toast"
        //setTimeout(() => { document.getElementById('alert-toast').style.display='none' }, $duration );
        //onLoad = "console.log('????????')"
        role = "alert"
        svg("stroke-current shrink-0 h-6 w-6 cursor-pointer") {
            attributes["fill"] = "none"
            attributes["viewbox"] = "0 0 24 24"
            onClick = "document.getElementById('alert-toast').style.display='none'"
            path {
                attributes["stroke-linecap"] = "round"
                attributes["stroke-linejoin"] = "round"
                attributes["stroke-width"] = "2"
                attributes["d"] = "M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
            }
        }
        span { +"""$msg""" }
    }

    script {
        unsafe {
            raw("""
                    setTimeout(() => {
                        document.getElementById('alert-toast').style.display = 'none'
                    }, 4000);
                
            """.trimIndent())
        }
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


