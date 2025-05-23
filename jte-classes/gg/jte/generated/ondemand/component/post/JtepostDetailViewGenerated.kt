@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.post
import freeapp.life.stella.api.web.dto.PostDetailDto
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostDetailViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/post/postDetailView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,2,2,2,2,2,9,9,9,9,13,13,13,13,13,13,15,15,17,17,17,17,24,24,27,27,28,28,29,29,32,32,34,35,35,35,35,36,36,40,40,41,41,41,2,3,3,3,3,3)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, userId:Long?, post:PostDetailDto) {
		jteOutput.writeContent("\n<link rel=\"stylesheet\" href=\"https://uicdn.toast.com/editor/latest/toastui-editor.min.css\" />\n\n<div id=\"post-detail\" class=\"w-3/6 mx-auto mb-5 py-5\">\n    <div class=\"sm:text-3xl md:text-3xl lg:text-3xl text-black xl:text-4xl font-bold cursor-pointer\">\n        ")
		jteOutput.setContext("div", null)
		jteOutput.writeUserContent(post.title)
		jteOutput.writeContent("\n    </div>\n    <div class=\"font-light text-gray-600 mt-2 flex justify-between\">\n        <h1 class=\"font-bold text-gray-700 hover:underline\">\n            By ")
		jteOutput.setContext("h1", null)
		jteOutput.writeUserContent(post.username)
		jteOutput.writeContent(" · ")
		jteOutput.setContext("h1", null)
		jteOutput.writeUserContent(post.createdAt)
		jteOutput.writeContent("\n        </h1>\n        ")
		if (userId != null && (post.userId == userId)) {
			jteOutput.writeContent("\n            <div class=\"flex gap-x-1\">\n                <div hx-get=\"/post/editor?postId=")
			jteOutput.setContext("div", "hx-get")
			jteOutput.writeUserContent(post.id)
			jteOutput.setContext("div", null)
			jteOutput.writeContent("\" hx-swap=\"outerHTML\" hx-target=\"#post-detail\" class=\"btn\">\n                    수정\n                </div>\n                <div>\n                    <button class=\"btn\" onclick=\"post_delete_modal.showModal()\">삭제</button>\n                </div>\n            </div>\n        ")
		}
		jteOutput.writeContent("\n    </div>\n    <div class=\"mt-2\">\n        ")
		for (postTag in post.postTags) {
			jteOutput.writeContent("\n            ")
			gg.jte.generated.ondemand.component.post.JtetagViewGenerated.render(jteOutput, jteHtmlInterceptor, postTag);
			jteOutput.writeContent("\n        ")
		}
		jteOutput.writeContent("\n    </div>\n\n    ")
		gg.jte.generated.ondemand.component.post.JtepostModalViewGenerated.render(jteOutput, jteHtmlInterceptor, post);
		jteOutput.writeContent(" \n    \n    ")
		jteOutput.writeContent("\n    <div id=\"post-detail-viewer-content-")
		jteOutput.setContext("div", "id")
		jteOutput.writeUserContent(post.id)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("\">\n        ")
		jteOutput.writeUnsafeContent(post.content)
		jteOutput.writeContent("\n    </div>\n</div>\n\n")
		gg.jte.generated.ondemand.component.comment.JtecommentSectionViewGenerated.render(jteOutput, jteHtmlInterceptor, post, userId);
		jteOutput.writeContent(" \n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val userId = params["userId"] as Long?
		val post = params["post"] as PostDetailDto
		render(jteOutput, jteHtmlInterceptor, userId, post);
	}
}
}
