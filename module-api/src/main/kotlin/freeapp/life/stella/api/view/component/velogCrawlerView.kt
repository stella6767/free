package freeapp.life.stella.api.view.component

import kotlinx.html.*

fun DIV.velogCrawlerView(html:String){

    div {
        classes = setOf("container", "mx-auto", "px-4", "bg-white")
        div {
            classes = setOf("flex", "justify-between", "mb-5")
            input {
                id = "username"
                attributes["name"] = "username"
                attributes["placeholder"] = "input username"
                classes = setOf("input", "input-bordered", "w-full", "max-w-xs", "mt-3")
                attributes["required"] = ""
            }
            button {
                a {
                    id = "downloader"
                    href = "#"
                    classes = setOf("font-extrabold")
                    +"가져오기"
                }
            }
        }

        htmlViewer(html)

    }


}