@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.post
import freeapp.life.stella.api.web.dto.PostDetailDto
import freeapp.life.stella.api.util.toJson // Assuming this utility is available
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostEditorViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/post/postEditorView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,1,3,3,3,3,3,14,14,14,14,14,14,14,14,14,14,17,17,17,17,17,17,17,17,17,31,31,31,31,31,31,31,31,31,32,32,32,32,32,32,32,32,32,37,37,37,40,40,40,3,3,3,3,3)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, post:PostDetailDto?) {
		jteOutput.writeContent("\n<link rel=\"stylesheet\" href=\"https://uicdn.toast.com/editor/latest/toastui-editor.min.css\" />\n<script src=\"https://cdn.jsdelivr.net/npm/@yaireo/tagify\"></script>\n<link href=\"https://cdn.jsdelivr.net/npm/@yaireo/tagify/dist/tagify.css\" rel=\"stylesheet\" type=\"text/css\" />\n\n<form id=\"post-form\" class=\"py-4 flex flex-col gap-y-3 mb-3\">\n    <h1 class=\"text-center text-black mb-4 text-4xl font-extrabold leading-none tracking-tight md:text-5xl lg:text-6xl\">\n        TOAST UI Editor\n    </h1>\n    <div class=\"flex justify-center gap-x-3 w-6/12 mx-auto\">\n        <input type=\"text\"")
		val __jte_html_attribute_0 = post?.username ?: ""
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" value=\"")
			jteOutput.setContext("input", "value")
			jteOutput.writeUserContent(__jte_html_attribute_0)
			jteOutput.setContext("input", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" name=\"username\" placeholder=\"input your username. if you do not input, default name is Anonymous\" class=\"input input-bordered w-full justify-self-center\" />\n    </div>\n    <div class=\"flex w-6/12 mx-auto justify-center\">\n        <input type=\"text\" required")
		val __jte_html_attribute_1 = post?.title ?: ""
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" value=\"")
			jteOutput.setContext("input", "value")
			jteOutput.writeUserContent(__jte_html_attribute_1)
			jteOutput.setContext("input", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" name=\"title\" placeholder=\"input your title\" class=\"input input-bordered justify-self-center w-full\" />\n    </div>\n    <div class=\"flex w-6/12 mx-auto justify-center\">\n        <input placeholder=\"태그를 입력하고 Enter 누르면 됩니다.\" name=\"hashtag\" tabindex=\"2\" class=\"input input-bordered justify-self-center w-full text-white\" value=\"\" type=\"text\" />\n    </div>\n    <div id=\"editor\" class=\"border-2 w-1/2 mx-auto\">\n\n    </div>\n    <br />\n    <div class=\"flex justify-center gap-x-2\">\n        <button id=\"post-submit-btn\" class=\"btn btn-success\">제출</button>\n        <button type=\"button\" hx-on-click=\"cancelPost()\" class=\"btn btn-warning\">취소</button>\n    </div>\n\n    <input type=\"hidden\"")
		val __jte_html_attribute_2 = post?.id ?: 0L
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_2)) {
			jteOutput.writeContent(" value=\"")
			jteOutput.setContext("input", "value")
			jteOutput.writeUserContent(__jte_html_attribute_2)
			jteOutput.setContext("input", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" name=\"postId\" />\n    <input type=\"hidden\"")
		val __jte_html_attribute_3 = post?.postTags?.toJson() ?: "[]"
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_3)) {
			jteOutput.writeContent(" value=\"")
			jteOutput.setContext("input", "value")
			jteOutput.writeUserContent(__jte_html_attribute_3)
			jteOutput.setContext("input", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" name=\"originTags\" />\n</form>\n\n<script src=\"https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js\"></script>\n<script>\n    var contentValue = '")
		jteOutput.setContext("script", null)
		jteOutput.writeUserContent(post?.content?.replace("'", "\\'")?.replace("\n", "\\n") ?: "please input content")
		jteOutput.writeContent("';\n</script>\n<script src=\"/js/post.js\" defer></script>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val post = params["post"] as PostDetailDto?
		render(jteOutput, jteHtmlInterceptor, post);
	}
}
}
