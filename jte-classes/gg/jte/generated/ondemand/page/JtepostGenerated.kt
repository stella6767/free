@file:Suppress("ktlint")
package gg.jte.generated.ondemand.page
import org.springframework.data.domain.Page
import freeapp.life.stella.api.web.dto.PostCardDto
import org.springframework.util.StringUtils // Assuming this is for StringUtils.hasText
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostGenerated {
companion object {
	@JvmField val JTE_NAME = "page/post.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,1,2,4,4,4,4,4,7,7,7,7,9,9,11,11,11,13,13,13,4,5,5,5,5,5)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, keyword:String, posts:Page<PostCardDto>) {
		jteOutput.writeContent("\n")
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, freeapp.life.stella.api.util.isLoggedIn (), object : gg.jte.html.HtmlContent {
			override fun writeTo(jteOutput:gg.jte.html.HtmlTemplateOutput) {
				jteOutput.writeContent("\n\n    ")
				gg.jte.generated.ondemand.component.post.JtepostViewGenerated.render(jteOutput, jteHtmlInterceptor, keyword, posts);
				jteOutput.writeContent("\n\n")
			}
		});
		jteOutput.writeContent("\n\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val keyword = params["keyword"] as String
		val posts = params["posts"] as Page<PostCardDto>
		render(jteOutput, jteHtmlInterceptor, keyword, posts);
	}
}
}
