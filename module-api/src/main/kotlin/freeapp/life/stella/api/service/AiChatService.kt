package freeapp.life.stella.api.service

import freeapp.life.stella.api.web.dto.AiChatReqDto
import mu.KotlinLogging
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap


@Service
class AiChatService(
    private val builder: ChatClient.Builder,
) {

    private val log = KotlinLogging.logger { }

    private val chatClient = builder.build()

    private val emitters = ConcurrentHashMap<String, SseEmitter>()

    fun sendAiResponse(chatReqDto: AiChatReqDto, uniqueId: Long) {

        // 사용자별 emiiter 가져오기 없으면 만들어내기
        val emitter = emitters.computeIfAbsent(chatReqDto.clientId) {
            SseEmitter(Long.MAX_VALUE)
        }

        val aiResponse = chatClient.prompt(chatReqDto.msg)
            .stream()
            .content()

        aiResponse
            //.delayElements(Duration.ofMillis(500))
            .doOnNext {
                emitter.send(SseEmitter.event()
                    .name("ai-response")
                    .id(uniqueId.toString())
                    .data(it))
            }
            .subscribe()
    }


    fun connectSse(
        clientId:String
    ): SseEmitter {

        log.info { clientId }

        val emitter = SseEmitter(60 * 1000L)

        emitters[clientId] = emitter
        // 클라이언트가 연결을 종료하면 제거
        emitter.onCompletion {
            log.info { "종료" }
            emitters.remove(clientId)
        }
        emitter.onTimeout { emitters.remove(clientId) }
        emitter.onError { emitters.remove(clientId) }

        return emitter
    }



}
