package freeapp.life.stella.api.web


import freeapp.life.stella.api.web.dto.ChatSendDto
import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
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
    private val templateEngine: TemplateEngine
) {

    private val log = KotlinLogging.logger { }

    @GetMapping("/chat")
    fun chatPage(): String {

        return "page/chat"
    }



    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(
        @Payload chatSendDto: ChatSendDto
    ): String {
        log.info("service: $chatSendDto")

        val output = StringOutput()
        templateEngine.render("component/chat/chatBoxTemplate.kte", chatSendDto, output)

        return output.toString() // 렌더링된 HTML 문자열 반환
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(
        @Payload chatSendDto: ChatSendDto,
        headerAccessor: SimpMessageHeaderAccessor
    ): String {
        log.info("요청옴: $chatSendDto ,  $headerAccessor")
        val output = StringOutput()
        templateEngine.render("component/chat/chatBoxTemplate.kte", chatSendDto, output)

        return output.toString()
    }

}
