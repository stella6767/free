@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.util
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JteprogressViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/util/progressView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(4,4,4,4,4,4,4,4,4,4,4,4,4,4,4)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?) {
		jteOutput.writeContent("<div id=\"loading-spinner\" class=\"my-indicator absolute right-1/2 bottom-1/2 transform translate-x-1/2 translate-y-1/2 z-20\" style=\"display: none;\">\n    <div class=\"border-t-transparent border-solid animate-spin rounded-full border-blue-400 border-8 h-64 w-64\">\n    </div>\n</div>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
}
