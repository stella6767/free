package com.stella.free.core.chat.api

import com.stella.free.core.chat.dto.ChatResDto
import com.stella.free.core.chat.dto.ChatSendDto
import com.stella.free.global.util.logger


import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller


@Controller
class ChatController(
) {

    private val log = logger()

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



    private fun chatBoxTemplate(chatSendDto: ChatSendDto): String {
        return """      
            <div class="chat-message mt-2">
                <div id="chat-box-comment"
                        class="flex">
                    <div 
                            class="flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1">
                        <div>
                                    <span id="chat-message-box" class="px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600">
                                        ${chatSendDto.message}
                                    </span>
                        </div>
                        <div class="max-w-xs mx-2 text-xs font-bold">
                            ${chatSendDto.sender}
                        </div>
                    </div>
                </div>
            </div>          
            """.trimIndent()
    }





}