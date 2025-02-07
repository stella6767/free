package freeapp.life.stella.api.controller

import freeapp.life.stella.api.dto.AiChatReqDto
import freeapp.life.stella.api.service.AiChatService
import freeapp.life.stella.api.view.component.chatMsgView
import freeapp.life.stella.api.view.component.gptView
import freeapp.life.stella.api.view.page.renderComponent
import freeapp.life.stella.api.view.page.renderPageWithLayout
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.atomic.AtomicLong




@RestController
@RequestMapping("/ai")
class AiChatController(
    private val aiChatService: AiChatService
) {

    // 요청마다 증가하는 전역 AtomicLong 변수 (스레드 안전)
    private val aiResponseDivId = AtomicLong()

    @GetMapping("/chat")
    fun chatView(): String? {
        return renderPageWithLayout {
            gptView()
        }
    }

    @PostMapping("/chat")
    fun chat(chatReqDto: AiChatReqDto): String {

        val uniqueId =
            aiResponseDivId.incrementAndGet()

        aiChatService.sendAiResponse(chatReqDto, uniqueId)

        val userChatView = renderComponent {
            chatMsgView(
                true,
                chatReqDto.msg,
                uniqueId
            )
            chatMsgView(
                false,
                "",
                uniqueId
            )
        }

        return userChatView
    }

    @GetMapping(path = ["/chat-sse/{clientId}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamChat(
        @PathVariable clientId: String,
    ): SseEmitter {

        return aiChatService.connectSse(clientId)
    }

}
