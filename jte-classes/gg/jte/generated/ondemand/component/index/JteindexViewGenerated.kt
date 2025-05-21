@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.index
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JteindexViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/index/indexView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,8,8,8,15,15,15,0,1,1,1,1,1)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, value:String, isCenter:Boolean = true) {
		jteOutput.writeContent("\n<div class=\"hero min-h-screen\" style=\"background-image: url(/img/background.jpg);\">\n    <div class=\"hero-overlay bg-opacity-30\">\n        <div>\n            <div class=\"container mx-auto px-4\">\n                <div class=\"flex justify-between mb-5\">\n                    ")
		gg.jte.generated.ondemand.component.util.JtehtmlViewerGenerated.render(jteOutput, jteHtmlInterceptor, value, isCenter);
		jteOutput.writeContent("\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n<script src=\"https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js\"></script>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val value = params["value"] as String
		val isCenter = params["isCenter"] as Boolean? ?: true
		render(jteOutput, jteHtmlInterceptor, value, isCenter);
	}
}
}
