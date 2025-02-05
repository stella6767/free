package freeapp.life.stella.api.controller

import freeapp.life.stella.api.dto.ChatResDto
import freeapp.life.stella.api.dto.ChatSendDto
import freeapp.life.stella.api.view.component.chatBoxTemplate
import freeapp.life.stella.api.view.component.chatView
import freeapp.life.stella.api.view.page.renderPageWithLayout
import mu.KotlinLogging
import org.springframework.ai.autoconfigure.ollama.OllamaChatProperties
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.DefaultChatClient
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController



@RestController
class ChatController(
    private val builder: ChatClient.Builder,
) {

    private val chatClient: ChatClient = builder.build()

    private val log = KotlinLogging.logger {  }


    fun dd() {

        val chatProperties = OpenAiChatProperties()

        chatProperties.baseUrl = ""
        chatProperties.apiKey = ""

        //OpenAiApi(openAiProperties.getBaseUrl(), openAiProperties.getApiKey())

    }

    @GetMapping("/aichat")
    fun test(): String? {




        val chatProperties = OllamaChatProperties()

        //chatProperties.options.

//        builder.defaultAdvisors()
//
//        val build = OllamaOptions.builder().model("").build()
//
//
//        //OllamaChatModel.builder().defaultOptions()

        return chatClient.prompt()
            .user("hi")
            .call()
            .content()
    }

    @GetMapping("/chat")
    fun chatPage(): String {

        return renderPageWithLayout {
            chatView()
        }
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@Payload chatSendDto: ChatSendDto): ChatResDto {
        log.info("service: $chatSendDto")

        return ChatResDto(
            sender = chatSendDto.sender,
            html = chatBoxTemplate(chatSendDto)
        )
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(@Payload chatSendDto: ChatSendDto, headerAccessor: SimpMessageHeaderAccessor): ChatResDto {
        log.info("요청옴: $chatSendDto ,  $headerAccessor")
        return ChatResDto(
            sender = chatSendDto.sender,
            html = chatBoxTemplate(chatSendDto)
        )
    }







}
