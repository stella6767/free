@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.post
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostModalViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/post/postModalView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,7,7,7,7,7,23,23,23,0,0,0,0,0)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, post:freeapp.life.stella.api.web.dto.PostDetailDto) {
		jteOutput.writeContent("\n<dialog id=\"post_delete_modal\" class=\"modal\">\n    <form method=\"dialog\" class=\"modal-box\">\n        <div class=\"bg-white shadow w-full rounded-lg divide-y divide-gray-200\">\n            <div class=\"p-5\">\n                <div class=\"mt-3 space-x-2 flex justify-around\">\n                    <div hx-delete=\"/post/")
		jteOutput.setContext("div", "hx-delete")
		jteOutput.writeUserContent(post.id)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("\"\n                         hx-swap=\"none\"\n                         hx-on--after-request=\"location.href='/blog'\"\n                         class=\"btn\"\n                         id=\"post-delete-btn\"\n                         role=\"button\">\n                        삭제\n                    </div>\n                </div>\n            </div>\n        </div>\n        <div class=\"modal-action\">\n            <button type=\"submit\" class=\"btn\">Close</button>\n        </div>\n    </form>\n</dialog>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val post = params["post"] as freeapp.life.stella.api.web.dto.PostDetailDto
		render(jteOutput, jteHtmlInterceptor, post);
	}
}
}
