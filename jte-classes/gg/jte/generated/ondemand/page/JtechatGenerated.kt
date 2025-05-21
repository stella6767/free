@file:Suppress("ktlint")
package gg.jte.generated.ondemand.page
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtechatGenerated {
companion object {
	@JvmField val JTE_NAME = "page/chat.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(1,1,1,1,1,1,1,1,1,1,1,2,2,3,3,3,4,4,4,4,4,4,4)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?) {
		jteOutput.writeContent("\n")
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, false, object : gg.jte.html.HtmlContent {
			override fun writeTo(jteOutput:gg.jte.html.HtmlTemplateOutput) {
				jteOutput.writeContent("\n    ")
				gg.jte.generated.ondemand.component.chat.JtechatViewGenerated.render(jteOutput, jteHtmlInterceptor);
				jteOutput.writeContent("\n")
			}
		});
		jteOutput.writeContent("\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
}
