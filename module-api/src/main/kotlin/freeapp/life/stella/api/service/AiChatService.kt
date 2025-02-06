package freeapp.life.stella.api.service

import mu.KotlinLogging
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service


@Service
class AiChatService(
    private val builder: ChatClient.Builder,
) {

    private val log  = KotlinLogging.logger {  }

    private val chatClient = builder.build()

    fun saveChat(msg:String) {

        println(msg)
    }



}
