package com.stella.free.global.config

import com.stella.free.global.util.logger
import jakarta.websocket.*
import jakarta.websocket.server.ServerEndpoint
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(

) :  WebSocketMessageBrokerConfigurer {

    private val log = logger()

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/app") //서버에서 클라이언트로부터의 메시지를 받을 api의 prefix 설정
        registry.enableSimpleBroker("/topic/public")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")//client에서 websocket을 연결할 api를 설정, 여러개의 endpoint 설정가능
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }


}