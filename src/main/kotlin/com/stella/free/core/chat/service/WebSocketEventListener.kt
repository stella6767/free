package com.stella.free.core.chat.service

import com.stella.free.core.chat.dto.ChatDto
import com.stella.free.global.util.logger
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.time.LocalDateTime


@Component
class WebSocketEventListener(
    private val messagingTemplate: SimpMessagingTemplate,
) {

    private val log = logger()


    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectedEvent?) {
        log.info("Received a new web socket connection")
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {

        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        val username = headerAccessor.sessionAttributes?.get("username") as String?
        if (username != null) {
            log.info("User Disconnected : $username")

            val chatMessage = ChatDto(
                type = ChatDto.MessageType.LEAVE,
                sender = username,
                message = "",
                time = LocalDateTime.now().toString()
            )
            messagingTemplate.convertAndSend("/topic/public", chatMessage)
        }
    }

}