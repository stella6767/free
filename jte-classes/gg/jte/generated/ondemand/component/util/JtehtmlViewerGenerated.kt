@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.util
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtehtmlViewerGenerated {
companion object {
	@JvmField val JTE_NAME = "component/util/htmlViewer.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,5,5,5,5,5,7,7,7,10,10,10,0,1,1,1,1,1)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, value:String, isCenter:Boolean = true) {
		jteOutput.writeContent("\n<link rel=\"stylesheet\" href=\"https://uicdn.toast.com/editor/latest/toastui-editor.min.css\" />\n\n<div class=\"toastui-editor-contents text-neutral-content ")
		if (isCenter) {
			jteOutput.writeContent("text-center")
		}
		jteOutput.writeContent("\" style=\"overflow-wrap: break-word;\">\n    <div data-nodeid=\"1\">\n        ")
		jteOutput.setContext("div", null)
		jteOutput.writeUserContent(unsafeRaw(value))
		jteOutput.writeContent("\n    </div>\n</div>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val value = params["value"] as String
		val isCenter = params["isCenter"] as Boolean? ?: true
		render(jteOutput, jteHtmlInterceptor, value, isCenter);
	}
}
}
