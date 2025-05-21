package freeapp.life.stella.api.web


import freeapp.life.stella.api.web.dto.ChatSendDto
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class ChatController(

) {

    private val log = KotlinLogging.logger { }

    @GetMapping("/chat")
    fun chatPage(): String {

        return "page/chat"
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(
        model: Model,
        @Payload chatSendDto: ChatSendDto
    ): String {
        log.info("service: $chatSendDto")

        model.addAttribute("chatSendDto", chatSendDto)

        return "component/chat/chatBoxTemplate"
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(
        model: Model,
        @Payload chatSendDto: ChatSendDto,
        headerAccessor: SimpMessageHeaderAccessor
    ): String {
        log.info("요청옴: $chatSendDto ,  $headerAccessor")
        model.addAttribute("chatSendDto", chatSendDto)

        return "component/chat/chatBoxTemplate"
    }

}
