@file:Suppress("ktlint")
package gg.jte.generated.ondemand.page
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JteindexGenerated {
companion object {
	@JvmField val JTE_NAME = "page/index.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,3,3,3,3,5,5,7,7,7,8,8,8,0,1,1,1,1,1)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, value:String, isCenter:Boolean = true) {
		jteOutput.writeContent("\n")
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, false, object : gg.jte.html.HtmlContent {
			override fun writeTo(jteOutput:gg.jte.html.HtmlTemplateOutput) {
				jteOutput.writeContent("\n\n    ")
				gg.jte.generated.ondemand.component.index.JteindexViewGenerated.render(jteOutput, jteHtmlInterceptor, value, isCenter);
				jteOutput.writeContent("\n\n")
			}
		});
		jteOutput.writeContent("\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val value = params["value"] as String
		val isCenter = params["isCenter"] as Boolean? ?: true
		render(jteOutput, jteHtmlInterceptor, value, isCenter);
	}
}
}
