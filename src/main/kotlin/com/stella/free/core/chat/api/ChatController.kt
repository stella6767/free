package com.stella.free.core.chat.api

import com.stella.free.core.chat.dto.ChatDto
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
    fun sendMessage(@Payload chatDto: ChatDto): String {
        log.info("service: $chatDto")
        return chatBoxTemplate(chatDto)
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(@Payload chatDto: ChatDto, headerAccessor: SimpMessageHeaderAccessor): String {

        log.info("요청옴: $chatDto ,  $headerAccessor")

        return chatBoxTemplate(chatDto)
    }


    private fun chatBoxTemplate(chatDto: ChatDto): String {
        return """
            <div class="chat-message">
                <div class="flex items-end">              
                    <div class="flex flex-col space-y-2 text-xs max-w-xs mx-2 order-2 items-start">
                        <div>                    
                            <span class="px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600">
                                ${chatDto.message}
                            </span>
                        </div>
                    </div>
                    <div class="max-w-xs mx-2 stext-xs font-bold">
                        ${chatDto.sender}                   
                    </div>
                </div>
            </div>
    
    
            <div class="chat-message">
                <div class="flex items-end justify-end">
                    <div class="flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1 items-end">
                        <div><span class="px-4 py-2 rounded-lg inline-block rounded-br-none bg-blue-600 text-white ">Any updates on this issue? I'm getting the same error when trying to install devtools. Thanks</span>
                        </div>
                    </div>
                    <div class="max-w-xs mx-2 stext-xs font-bold">
                        username
                    </div>
                </div>
            </div>                       
            """.trimIndent()
    }





}