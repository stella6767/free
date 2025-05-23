@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.comment
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtecommentFormViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/comment/commentFormView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,5,5,5,5,5,8,8,8,8,8,8,8,8,8,9,9,9,9,13,13,14,14,14,14,14,14,14,14,14,15,15,18,18,18,18,18,18,18,18,18,19,19,19,19,19,19,19,19,19,28,28,28,0,1,2,2,2,2,2)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, userId:Long?, postId:Long, idAncestor:Long = 0L) {
		jteOutput.writeContent("\n<form class=\"bg-white rounded-lg border p-2 mx-auto\"\n      id=\"comment-form-")
		jteOutput.setContext("form", "id")
		jteOutput.writeUserContent(idAncestor)
		jteOutput.setContext("form", null)
		jteOutput.writeContent("\"\n      hx-post=\"/comment\"\n      hx-swap=\"beforeend\"\n      hx-target=\"")
		if (idAncestor != 0L) {
			jteOutput.writeContent("#comment-card-")
			jteOutput.setContext("form", "hx-target")
			jteOutput.writeUserContent(idAncestor)
			jteOutput.setContext("form", null)
		} else {
			jteOutput.writeContent("#comment-card-container")
		}
		jteOutput.writeContent("\"\n      hx-on=\"htmx:afterRequest: document.getElementById('comment-form-")
		jteOutput.setContext("form", "hx-on")
		jteOutput.writeUserContent(idAncestor)
		jteOutput.setContext("form", null)
		jteOutput.writeContent("').reset()\">\n\n    <div class=\"px-3 mb-2 mt-2\">\n        <input type=\"text\" required name=\"nickName\" placeholder=\"input your nickname\" class=\"input input-bordered\" />\n        ")
		if (userId != null) {
			jteOutput.writeContent("\n            <input type=\"hidden\" name=\"userId\"")
			val __jte_html_attribute_0 = userId
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
				jteOutput.writeContent(" value=\"")
				jteOutput.setContext("input", "value")
				jteOutput.writeUserContent(__jte_html_attribute_0)
				jteOutput.setContext("input", null)
				jteOutput.writeContent("\"")
			}
			jteOutput.writeContent(" />\n        ")
		}
		jteOutput.writeContent("\n    </div>\n\n    <input type=\"hidden\" name=\"postId\"")
		val __jte_html_attribute_1 = postId
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" value=\"")
			jteOutput.setContext("input", "value")
			jteOutput.writeUserContent(__jte_html_attribute_1)
			jteOutput.setContext("input", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" />\n    <input type=\"hidden\" name=\"idAncestor\"")
		val __jte_html_attribute_2 = idAncestor
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_2)) {
			jteOutput.writeContent(" value=\"")
			jteOutput.setContext("input", "value")
			jteOutput.writeUserContent(__jte_html_attribute_2)
			jteOutput.setContext("input", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" />\n\n    <div class=\"px-3 mb-2 mt-2\">\n        <textarea placeholder=\"comment\" name=\"content\" id=\"comment-text\" class=\"w-full bg-gray-100 rounded border border-gray-400 leading-normal resize-none h-20 py-2 px-3 font-medium placeholder-gray-700 focus:outline-none focus:bg-white text-black\"></textarea>\n    </div>\n    <div class=\"flex justify-end px-4\">\n        <input type=\"submit\" class=\"px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 cursor-pointer\" />\n    </div>\n</form>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val userId = params["userId"] as Long?
		val postId = params["postId"] as Long
		val idAncestor = params["idAncestor"] as Long? ?: 0L
		render(jteOutput, jteHtmlInterceptor, userId, postId, idAncestor);
	}
}
}
