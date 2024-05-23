package freeapp.life.stella.api.view.component

import kotlinx.html.*

//<form class="bg-white rounded-lg border p-2 mx-auto "
//id="comment-form-${idAncestor}"
//hx-post="/comment"
//hx-target="#comment-card-container"
//hx-swap="beforeend"
//hx-on="htmx:afterRequest: document.getElementById('comment-form-${idAncestor}').reset()"
//>
//${content}
//</form>

fun DIV.htmlToKotlinConverter(){

    form {
        classes = setOf("px-5", "py-5")
        attributes["hx-post"] = "/convert/html"
        attributes["hx-target"] = "#converter-output"
        attributes["hx-on--after-request"] = "javascript:document.getElementById('converter-input').value='';"

        div {
            label("block text-sm font-medium leading-6 text-gray-900") {
                htmlFor = "input"
                +"""HTML"""
            }
            textArea(classes = "textarea textarea-bordered textarea-lg w-full min-h-80") {
                id = "converter-input"
                placeholder = "HTML"
                name = "input"
            }
        }
        button(classes = "rounded-md w-full bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600") {
            id = "convert-button"
            type = ButtonType.submit
            +"Convert"

        }
        div {
            label("block text-sm font-medium leading-6 text-gray-900") {
                htmlFor = "output"
                +"""Kotlin"""
            }
            textArea(classes = "textarea textarea-bordered textarea-lg w-full min-h-80") {
                id = "converter-output"
                name = "output"
                readonly = true
            }
        }
    }






}