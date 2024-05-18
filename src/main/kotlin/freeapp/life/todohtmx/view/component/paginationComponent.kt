package freeapp.life.todohtmx.view.component

import freeapp.life.todohtmx.entity.Todo
import kotlinx.html.*
import org.springframework.data.domain.Page
import kotlin.math.floor


fun <T : Any> DIV.paginationViewComponent(page: Page<T>, endPoint:String) {

    var maxPage = 5
    var start =  floor ((page.number / maxPage).toDouble()) * maxPage + 1

    var end =
        if((start + maxPage -1 ) < page.totalPages)  start + maxPage -1 else page.totalPages

    div("flex justify-center") {
        div("join") {
            button {
                classes =  setOf("join-item btn")
                onClick = "location.href='/${endPoint}?page=0'"
                +"""First"""
            }

            button {
                classes =  setOf("join-item btn")
                if (page.isFirst) {
                    disabled = true
                }else{
                    onClick = "location.href='/${endPoint}?page=${page.number - 1}'"
                }
                +"<<"
            }


            for (i in start.toInt().. end.toInt()) {

                button(classes = "join-item btn btn-square"){
                    onClick = "window.location.href='/todos?page=${i-1}'"
                    if (i == page.number + 1){
                        classes += "btn-active  bg-accent"
                    }
                    +"$i"
                }
            }


            button(classes = "join-item btn") {
                if (page.isLast) {
                    disabled = true
                }else{
                    onClick = "location.href='/${endPoint}?page=${page.number + 1}'"
                }
                +">>"
            }

            button(classes = "join-item btn") {
                onClick = "location.href='/todos?page=${page.totalPages -1}'"
                +"""Last"""
            }




        }
    }
    br {
    }

}
