@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.post
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtetagViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/post/tagView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,2,2,2,2,2,4,4,4,6,6,6,0,0,0,0,0)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, tagName:String) {
		jteOutput.writeContent("\n<a href=\"/blog/tag?tagName=")
		jteOutput.setContext("a", "href")
		jteOutput.writeUserContent(tagName)
		jteOutput.setContext("a", null)
		jteOutput.writeContent("\"\n   class=\"h-8 inline-flex items-center bg-black text-white cursor-pointer no-underline font-medium text-base mr-3.5 px-4 rounded-2xl\">\n    ")
		jteOutput.setContext("a", null)
		jteOutput.writeUserContent(tagName)
		jteOutput.writeContent("\n</a>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val tagName = params["tagName"] as String
		render(jteOutput, jteHtmlInterceptor, tagName);
	}
}
}
