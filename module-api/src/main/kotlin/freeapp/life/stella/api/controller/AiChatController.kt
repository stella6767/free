package freeapp.life.stella.api.controller

import freeapp.life.stella.api.dto.AiChatReqDto
import freeapp.life.stella.api.service.AiChatService
import freeapp.life.stella.api.view.component.chatMsgView
import freeapp.life.stella.api.view.component.chatUserView
import freeapp.life.stella.api.view.component.gptView
import freeapp.life.stella.api.view.page.renderComponent
import freeapp.life.stella.api.view.page.renderPageWithLayout
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/ai")
class AiChatController(
    private val aiChatService: AiChatService
) {

    @GetMapping("/chat")
    fun chatView(): String? {
        return renderPageWithLayout {
            gptView()
        }
    }
    @PostMapping("/chat")
    fun chat(chatReqDto:AiChatReqDto): String {

        aiChatService.saveChat(chatReqDto.msg)
//
//        var viewId =
//            chatReqDto.viewId.toLong()

        return renderComponent {
            chatMsgView(
                true,
                chatReqDto.msg,
            )
        }
    }


}
