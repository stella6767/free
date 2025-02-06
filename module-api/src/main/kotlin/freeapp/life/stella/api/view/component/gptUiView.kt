package freeapp.life.stella.api.view.component

import kotlinx.html.*




fun DIV.chatMsgView(
    isUser: Boolean = false,
    msg: String,
    //viewId: Long = 0,
) {

    if (isUser) {
        div {
            //id = "chat-messages-${viewId}"
            classes = setOf("p-3", "rounded-lg", "max-w-xs", "ml-auto", "bg-[#414158]", "text-white")
            +msg
        }
    } else {
        div {
            id = "ai-response"
            classes = setOf("p-3", "bg-[#525252]", "border", "rounded-lg", "max-w-xs")
            div {
                id = "ai-content"
                +msg
            }
        }
    }

}

fun DIV.gptView(
) {

    classes = setOf("bg-gray-100", "h-screen", "flex", "flex-col", "items-center")

    div {
        classes = setOf("w-full", "max-w-2xl", "h-screen", "flex", "flex-col", "bg-white", "shadow-lg")
        div {
            classes = setOf("p-4", "bg-gray-800", "text-white", "text-lg", "font-semibold", "border-b", "text-center")
            +"AI Assistant"
        }
        div {
            id = "chat-messages"
            classes = setOf("flex-1", "overflow-y-auto", "p-4", "bg-gray-50", "space-y-4")
            div {
                classes = setOf("p-3", "bg-[#525252]", "border", "rounded-lg", "max-w-xs")
                +"안녕하세요! 무엇을 도와드릴까요? 😊"
            }



        }
        form {
            classes = setOf("p-4", "bg-white", "border-t", "flex", "items-center", "gap-2")

            attributes["hx-post"] = "/ai/chat"
            //attributes["hx-include"] = "#user-input"
            attributes["hx-target"] = "#chat-messages"
            attributes["hx-swap"] = "beforeend"
            attributes["hx-trigger"] = "keydown[!shiftKey && key=='Enter'] from:#user-input"

            input {
                type = InputType.hidden
                id = "client-id"
                name = "clientId"
            }

            textArea {
                id = "user-input"
                name = "msg"
                classes = setOf(
                    "flex-1",
                    "p-2",
                    "border",
                    "rounded-md",
                    "focus:ring-2",
                    "focus:ring-blue-500",
                    "outline-none",
                    "h-100",
                    "max-h-[300px]"
                )
                attributes["hx-on::input"] = "this.style.height = 'auto'; this.style.height = this.scrollHeight + 'px'"

                attributes["placeholder"] = "메시지를 입력하세요..."
            }
            button {
                id = "user-input-btn"
                attributes["hx-trigger"] = "click"
                attributes["hx-post"] = "/ai/chat"
                attributes["hx-include"] = "#user-input"
                attributes["hx-target"] = "#chat-messages"
                attributes["hx-swap"] = "beforeend"
                //onClick = "sendMessage()"
                //type = ButtonType.submit
                //attributes["onclick"] = "sendMessage()"
                classes = setOf("px-4", "py-2", "bg-[#525252]", "text-white", "rounded-md", "hover:bg-blue-700")
                +"전송"
            }
        }
    }

    script {
        src = "/js/aichat.js"
    }
}




