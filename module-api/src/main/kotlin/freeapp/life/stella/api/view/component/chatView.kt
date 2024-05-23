package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.dto.ChatSendDto
import freeapp.life.stella.api.view.page.path
import kotlinx.html.*

fun DIV.chatView(){
    div {
        id = "chat-name-page"
        classes = setOf("bg-yellow-400", "h-screen", "overflow-hidden", "flex", "items-center", "justify-center")
        div {
            classes = setOf("bg-white", "lg:w-5/12", "md:6/12", "w-10/12", "shadow-3xl")
            div {
                classes = setOf("bg-gray-800", "absolute", "left-1/2", "transform", "-translate-x-1/2", "-translate-y-1/2", "rounded-full", "p-4", "md:p-8")
            }
            form {
                id = "chat-username-form"
                classes = setOf("p-12", "md:p-24")
                div {
                    classes = setOf("flex", "items-center", "text-lg", "mb-6", "md:mb-8")
                    svg {
                        classes = setOf("absolute", "ml-3")
                        attributes["width"] = "24"
                        attributes["viewbox"] = "0 0 24 24"
                        path {
                            attributes["d"] = "M20.822 18.096c-3.439-.794-6.64-1.49-5.09-4.418 4.72-8.912 1.251-13.678-3.732-13.678-5.082 0-8.464 4.949-3.732 13.678 1.597 2.945-1.725 3.641-5.09 4.418-3.073.71-3.188 2.236-3.178 4.904l.004 1h23.99l.004-.969c.012-2.688-.092-4.222-3.176-4.935z"
                        }
                    }
                    input {
                        required = true
                        type = InputType.text
                        id = "username"
                        classes = setOf("bg-gray-200", "pl-12", "py-2", "md:py-4", "focus:outline-none", "w-full")
                        attributes["placeholder"] = "Username"
                    }
                }
                button {
                    classes = setOf("bg-gradient-to-b", "from-gray-700", "to-gray-900", "font-medium", "p-2", "md:p-4", "text-white", "uppercase", "w-full")
                    +"오픈 채팅방 접속"
                }
            }
        }
    }
    div {
        id = "chat-page"
        classes = setOf("flex-1", "p:2", "sm:p-6", "justify-between", "flex", "flex-col", "container", "mx-auto", "hidden")
        div {
            id = "message-area"
            classes = setOf("overflow-y-auto", "")
            attributes["style"] = "height: 500px;"
        }
        div {
            classes = setOf("border-t-2", "border-gray-200", "px-4", "pt-4", "mb-2", "sm:mb-0")
            div {
                classes = setOf("relative", "flex")
                input {
                    id = "message-input"
                    type = InputType.text
                    attributes["placeholder"] = "Write your message!"
                    classes = setOf("w-full", "focus:outline-none", "focus:placeholder-gray-400", "text-gray-600", "placeholder-gray-600", "pl-12", "bg-gray-200", "rounded-md", "py-3")
                }
                div {
                    classes = setOf("absolute", "right-0", "items-center", "inset-y-0", "hidden", "sm:flex")
                    button {
                        id = "message-form"
                        type = ButtonType.button
                        classes = setOf("inline-flex", "items-center", "justify-center", "rounded-lg", "px-4", "py-3", "transition", "duration-500", "ease-in-out", "text-white", "bg-blue-500", "hover:bg-blue-400", "focus:outline-none")
                        span {
                            classes = setOf("font-bold")
                            +"Send"
                        }
                        svg {
                            attributes["xmlns"] = "http://www.w3.org/2000/svg"
                            attributes["viewbox"] = "0 0 20 20"
                            attributes["fill"] = "currentColor"
                            classes = setOf("h-6", "w-6", "ml-2", "transform", "rotate-90")
                            path {
                                attributes["d"] = "M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"
                            }
                        }
                    }
                }
            }
        }
    }
    script {
        src = "https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"
    }
    script {
        src = "https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"
    }

    script {
        src = "/js/chat.js"
    }
}


fun chatBoxTemplate(chatSendDto: ChatSendDto): String {
    return """      
            <div class="chat-message mt-2">
                <div id="chat-box-comment"
                        class="flex">
                    <div 
                            class="flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1">
                        <div>
                                    <span id="chat-message-box" class="px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600">
                                        ${chatSendDto.message}
                                    </span>
                        </div>
                        <div class="max-w-xs mx-2 text-xs font-bold">
                            ${chatSendDto.sender}
                        </div>
                    </div>
                </div>
            </div>          
            """.trimIndent()
}
