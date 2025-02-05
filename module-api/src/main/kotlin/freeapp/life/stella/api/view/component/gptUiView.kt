package freeapp.life.stella.api.view.component

import kotlinx.html.*

fun DIV.gptMsgView(author:String){

    div("max-w-lg w-full mx-auto p-6 flex-1") {
        +"""<!-- Chat messages -->"""
        div("flex flex-col gap-4") {
            +"""<!-- Sample user message -->"""
            div("flex items-start justify-start") {
                div("bg-gray-800 rounded-lg p-4 max-w-md") {
                    p("text-sm font-medium mb-1") { +"""User""" }
                    p { +"""Hello! How can I help you today?""" }
                }
            }
            +"""<!-- Sample bot message -->"""
            div("flex items-start justify-end") {
                div("bg-gray-800 rounded-lg p-4 max-w-md") {
                    p("text-sm font-medium mb-1") { +"""ChatGPT""" }
                    p { +"""Hello there! I'm here to assist you.""" }
                }
            }
        }
    }

}


fun DIV.gptInputView(author:String){

    div("w-full px-6 py-4 bg-gray-800") {
        input(classes = "w-full px-4 py-2 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 bg-gray-900 text-white") {
            type = InputType.text
            placeholder = "Type your message..."
        }
    }

}
