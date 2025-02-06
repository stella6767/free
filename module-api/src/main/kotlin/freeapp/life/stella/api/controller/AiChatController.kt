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
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap


@RestController
@RequestMapping("/ai")
class AiChatController(
    private val aiChatService: AiChatService
) {


    // 하나의 SseEmitter를 공유하면서 클라이언트별 이벤트 이름으로 구분
    private val emitter = SseEmitter(Long.MAX_VALUE)  // 긴 타임아웃을 설정하여 서버 연결 유지

    // 클라이언트별 이벤트를 구분하기 위해 UUID 저장
    private val clientEventNames: MutableMap<String, String> = ConcurrentHashMap()


    @GetMapping("/chat")
    fun chatView(): String? {
        return renderPageWithLayout {
            gptView()
        }
    }
    @PostMapping("/chat")
    fun chat(chatReqDto:AiChatReqDto): String {

        val aiResponse =
            aiChatService.getAIResponse(chatReqDto.msg)

        val clientId = chatReqDto.clientId

        clientEventNames[clientId] = clientId

        aiResponse
            .delayElements(Duration.ofMillis(500))
            .doOnNext {
                // 클라이언트별 이벤트 이름을 기반으로 메시지 전송
                println("????")
                emitter.send(SseEmitter.event().name(clientId).data(it))
            }
            .doFinally {
                // 완료 시 클라이언트와 이벤트 이름 제거 (필요 시)
                clientEventNames.remove(clientId)
            }
            .subscribe()

        val userChatView = renderComponent {
            chatMsgView(
                true,
                chatReqDto.msg,
            )
            chatMsgView(
                false,
                "",
            )
        }

        return userChatView
    }

    @GetMapping(path = ["/chat-sse/{clientId}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamChat(
        @PathVariable clientId:String,
    ): SseEmitter {

        println(clientId)
//        val eventName =
//            clientEventNames[clientId] ?: throw IllegalArgumentException("cant find sse client name")
        //clientEventNames[clientId] = clientId

        emitter.send(SseEmitter.event()
            .name(clientId)
            .data("connected!"));

        return emitter.apply {
            onTimeout {
                // 타임아웃 처리 (연결 종료 시)
                emitter.complete()
                clientEventNames.remove(clientId)
            }
        }
    }
}
