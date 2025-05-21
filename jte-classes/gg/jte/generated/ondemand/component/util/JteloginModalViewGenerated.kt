@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.util
import freeapp.life.stella.storage.entity.type.SignType
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JteloginModalViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/util/loginModalView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,10,10,10,10,10,10,10,10,13,13,13,13,15,15,15,15,15,15,15,15,15,17,17,17,21,21,30,30,30,30,30,30,30)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?) {
		jteOutput.writeContent("\n@val signTypes = SignType.values()\n\n<dialog id=\"login_modal\" class=\"modal\">\n    <form method=\"dialog\" class=\"modal-box\">\n        <h1 class=\"font-bold text-center text-2xl mb-5\">Social Login</h1>\n        <div class=\"bg-white shadow w-full rounded-lg divide-y divide-gray-200\">\n            <div class=\"p-5\">\n                <div class=\"mt-3 grid space-y-4\">\n                    ")
		for (signType in signTypes) {
			jteOutput.writeContent("\n                        <button type=\"button\"\n                        class=\"group h-12 px-6 border-2 border-gray-300 rounded-full transition duration-300 hover:border-blue-400 focus:bg-blue-50 active:bg-blue-100\"\n                        onclick=\"location.href='")
			jteOutput.setContext("button", "onclick")
			jteOutput.writeUserContent(signType.authorizationUrl)
			jteOutput.setContext("button", null)
			jteOutput.writeContent("'\">\n                        <div class=\"relative flex items-center space-x-4 justify-center\">\n                            <img")
			val __jte_html_attribute_0 = signType.imgUrl
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
				jteOutput.writeContent(" src=\"")
				jteOutput.setContext("img", "src")
				jteOutput.writeUserContent(__jte_html_attribute_0)
				jteOutput.setContext("img", null)
				jteOutput.writeContent("\"")
			}
			jteOutput.writeContent(" class=\"absolute left-0 w-5\" alt=\"logo\" />\n                            <span class=\"block w-max font-semibold tracking-wide text-gray-700 text-sm transition duration-300 group-hover:text-blue-600 sm:text-base\">\n                                    Continue with ")
			jteOutput.setContext("span", null)
			jteOutput.writeUserContent(signType.clientName)
			jteOutput.writeContent("\n                                </span>\n                        </div>\n                        </button>\n                    ")
		}
		jteOutput.writeContent("\n                </div>\n            </div>\n        </div>\n        <div class=\"modal-action\">\n            <button type=\"submit\" class=\"btn\">Close</button>\n        </div>\n    </form>\n</dialog>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
}
