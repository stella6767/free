@file:Suppress("ktlint")
package gg.jte.generated.ondemand.page
import freeapp.life.stella.api.web.dto.PostDetailDto
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostDetailGenerated {
companion object {
	@JvmField val JTE_NAME = "page/postDetail.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,2,2,2,2,2,6,6,6,6,8,8,10,10,10,11,11,11,2,3,3,3,3,3)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, userId:Long?, post:PostDetailDto) {
		jteOutput.writeContent("\n\n")
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, freeapp.life.stella.api.util.isLoggedIn (), object : gg.jte.html.HtmlContent {
			override fun writeTo(jteOutput:gg.jte.html.HtmlTemplateOutput) {
				jteOutput.writeContent("\n\n    ")
				gg.jte.generated.ondemand.component.post.JtepostDetailViewGenerated.render(jteOutput, jteHtmlInterceptor, userId, post);
				jteOutput.writeContent("\n\n")
			}
		});
		jteOutput.writeContent("\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val userId = params["userId"] as Long?
		val post = params["post"] as PostDetailDto
		render(jteOutput, jteHtmlInterceptor, userId, post);
	}
}
}
