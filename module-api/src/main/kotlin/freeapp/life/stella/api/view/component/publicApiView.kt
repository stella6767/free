package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.dto.Entry
import freeapp.life.stella.api.util.removeSpecialCharacters
import kotlinx.html.*


fun DIV.publicApiPageView(
    categories: List<String>

) {

    div {
        classes = setOf("container", "mx-auto", "px-4")
        div {
            classes = setOf("tabs", "tabs-boxed")
            for (category in categories) {
                tabView(category, categories.first())
            }
        }

        if (categories.isNotEmpty()) {
            tableView(categories.first())
        }
    }

}


fun DIV.tableView(
    category: String
) {
    div {
        classes = setOf("overflow-x-auto")
        id = "category-table"
        attributes["hx-get"] = "/table/${category}"
        attributes["hx-trigger"] = "load"
    }
}

fun DIV.tableBodyView(
    fieldNames: List<String>,
    entries: List<Entry>
) {
    table {
        classes = setOf("table")
        thead {
            tr {
                for (name in fieldNames) {
                    th {
                        classes = setOf("text-black")
                        +"$name"
                    }
                }
            }
        }
        tbody {

            for (entry in entries) {
                tr {
                    classes = setOf("text-black")
                    id = "${entry.Link}"
                    td {
                        +"${entry.API}"
                    }
                    td {
                        +"${entry.Auth}"
                    }
                    td {
                        +"${entry.Category}"
                    }
                    td {
                        +"${entry.Cors}"
                    }
                    td {
                        +"${entry.Description}"
                    }
                    td {
                        +"${entry.HTTPS}"
                    }
                    td {
                        a {
                            href = "${entry.Link}"
                            +"${entry.Link}"
                        }
                    }
                }
            }
        }
    }
}


fun DIV.tabView(
    category: String,
    activeCategory: String
) {
    val categoryTabId = "#category-tab-${category.removeSpecialCharacters()}"
    val activeCategoryTabId = "#category-tab-${activeCategory.removeSpecialCharacters()}"

    div {
        a {
            id = "$categoryTabId"
            classes = setOf("cursor-pointer", "tab", "tab-lifted", "m-tab")
            attributes["hx-get"] = "/table/${category}"
            attributes["hx-target"] = "#category-table"
            //attributes["onclick"] = "showActive('${category.removeSpecialCharacters()}')"
            if (categoryTabId == activeCategoryTabId) {
                classes += "tab-active"
            }
            attributes["hx-on:htmx-after-on-load"] =
                """
                   let currentTab = document.querySelector('.tab-active');                 
                   currentTab.classList.remove('tab-active')
                   let newTab = event.target                  
                   newTab.classList.add('tab-active')                            
                """.trimIndent()

            +"$category"
        }
    }
}