package freeapp.life.todohtmx.view.component

import freeapp.life.todohtmx.entity.Todo
import kotlinx.html.*
import org.springframework.data.domain.Page
import kotlin.math.floor


fun <T : Any> DIV.paginationViewComponent(
    page: Page<T>,
    endPoint: String,
    targetTagId:String,
) {

    var maxPage = 5
    var start = floor((page.number / maxPage).toDouble()) * maxPage + 1

    var end =
        if ((start + maxPage - 1) < page.totalPages) start + maxPage - 1 else page.totalPages

    div("flex justify-center") {

        id = "page-view"

        div("join") {
            attributes["hx-ext"] = "multi-swap"

            button {
                classes = setOf("join-item btn")
                attributes["hx-trigger"] = "click"
                attributes["hx-get"] = "/${endPoint}?page=0"
                attributes["hx-swap"] = "multi:#$targetTagId,#page-view"
                +"""First"""
            }

            button {
                classes = setOf("join-item btn")
                if (page.isFirst) {
                    disabled = true
                } else {
                    attributes["hx-trigger"] = "click"
                    attributes["hx-get"] = "/${endPoint}?page=${page.number - 1}"
                    attributes["hx-swap"] = "multi:#$targetTagId,#page-view"
                }
                +"<<"
            }


            for (i in start.toInt()..end.toInt()) {

                button(classes = "join-item btn btn-square") {

                    //onClick = "window.location.href='/todos?page=${i-1}'"
                    attributes["hx-trigger"] = "click"
                    attributes["hx-get"] = "/${endPoint}?page=${i - 1}"
                    attributes["hx-swap"] = "multi:#$targetTagId,#page-view"
                    if (i == page.number + 1) {
                        classes += "btn-active  bg-accent"
                    }
                    +"$i"
                }
            }


            button(classes = "join-item btn") {
                if (page.isLast) {
                    disabled = true
                } else {
                    attributes["hx-trigger"] = "click"
                    attributes["hx-get"] = "/${endPoint}?page=${page.number + 1}"
                    attributes["hx-swap"] = "multi:#$targetTagId,#page-view"
                }
                +">>"
            }

            button(classes = "join-item btn") {
                attributes["hx-trigger"] = "click"
                attributes["hx-get"] = "/${endPoint}?page=${page.totalPages - 1}"
                attributes["hx-swap"] = "multi:#$targetTagId,#page-view"
                +"""Last"""
            }
        }
    }
    br {
    }

}
