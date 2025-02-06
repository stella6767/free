package freeapp.life.stella.api.service

import mu.KotlinLogging
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class AiChatService(
    private val builder: ChatClient.Builder,
) {

    private val log = KotlinLogging.logger { }

    private val chatClient = builder.build()

    fun getAIResponse(message: String): Flux<String> {

        return chatClient.prompt(message)
            .stream()
            .content()
    }


}
