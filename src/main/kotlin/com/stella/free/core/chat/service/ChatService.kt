package com.stella.free.core.chat.service

import com.stella.free.core.chat.dto.ChatDto
import com.stella.free.global.util.logger
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Service


@Service
class ChatService {

    private val log = logger()

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@Payload chatDto: ChatDto): ChatDto {
        log.info("service: $chatDto")
        return chatDto
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(@Payload chatMessage: ChatDto, headerAccessor: SimpMessageHeaderAccessor): ChatDto {
        System.out.println(("요청옴" + chatMessage.toString()).toString() + " ,  " + headerAccessor)
        //SimpMessageHeaderAccessor 오직 1,
        //headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage
    }

}