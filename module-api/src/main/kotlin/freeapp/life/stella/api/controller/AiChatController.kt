package freeapp.life.stella.api.controller

import freeapp.life.stella.api.dto.AiChatReqDto
import freeapp.life.stella.api.service.AiChatService
import freeapp.life.stella.api.view.component.chatMsgView
import freeapp.life.stella.api.view.component.gptView
import freeapp.life.stella.api.view.page.renderComponent
import freeapp.life.stella.api.view.page.renderPageWithLayout
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.time.Duration


@RestController
@RequestMapping("/ai")
class AiChatController(
    private val aiChatService: AiChatService
) {

    // SSE로 스트리밍할 메시지를 발행하기 위한 싱글턴 Sink (브로드캐스터)
    private val chatSink: Sinks.Many<String> =
        Sinks.many().unicast().onBackpressureError()


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

        aiResponse
            .delayElements(Duration.ofMillis(500))
            .doOnNext {
                chatSink.tryEmitNext(it)
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

    @GetMapping(path = ["/chat-sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamChat(
        @RequestParam userId:String,
    ): Flux<String> {

        // Sink에 발행된 메시지를 클라이언트로 스트리밍

        println(userId)

        return chatSink.asFlux()
    }
}
