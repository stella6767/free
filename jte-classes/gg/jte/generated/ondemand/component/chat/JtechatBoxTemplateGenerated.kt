@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.chat
import freeapp.life.stella.api.web.dto.ChatSendDto
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtechatBoxTemplateGenerated {
companion object {
	@JvmField val JTE_NAME = "component/chat/chatBoxTemplate.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,1,1,1,1,1,9,9,9,9,13,13,13,19,19,19,1,1,1,1,1)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, chatSendDto:ChatSendDto) {
		jteOutput.writeContent("\n<div class=\"chat-message mt-2\">\n    <div id=\"chat-box-comment\" class=\"flex\">\n        <div class=\"flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1\">\n            <div>\n                <span id=\"chat-message-box\"\n                      class=\"px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600\">\n                    ")
		jteOutput.setContext("span", null)
		jteOutput.writeUserContent(chatSendDto.message)
		jteOutput.writeContent("\n                </span>\n            </div>\n            <div class=\"max-w-xs mx-2 text-xs font-bold\">\n                ")
		jteOutput.setContext("div", null)
		jteOutput.writeUserContent(chatSendDto.sender)
		jteOutput.writeContent("\n            </div>\n        </div>\n    </div>\n</div>\n\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val chatSendDto = params["chatSendDto"] as ChatSendDto
		render(jteOutput, jteHtmlInterceptor, chatSendDto);
	}
}
}
