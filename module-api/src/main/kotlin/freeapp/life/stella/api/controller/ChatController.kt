package freeapp.life.stella.api.controller

import freeapp.life.stella.api.dto.ChatResDto
import freeapp.life.stella.api.dto.ChatSendDto
import freeapp.life.stella.api.view.component.chatBoxTemplate
import freeapp.life.stella.api.view.component.chatView
import freeapp.life.stella.api.view.component.todosViewWithPage
import freeapp.life.stella.api.view.page.renderPageWithLayout
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ChatController(

) {

    private val log = KotlinLogging.logger {  }

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