@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.comment
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtecommentSectionViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/comment/commentSectionView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,6,6,6,9,9,10,10,11,11,14,14,14,0,1,1,1,1,1)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, post:freeapp.life.stella.api.web.dto.PostDetailDto, userId:Long?) {
		jteOutput.writeContent("\n<div class=\"antialiased mx-auto w-3/6 mt-10\">\n    <h3 class=\"mb-4 text-lg font-semibold text-gray-900\">Comments</h3>\n\n    ")
		gg.jte.generated.ondemand.component.comment.JtecommentFormViewGenerated.render(jteOutput, jteHtmlInterceptor, userId, post.id, 0L);
		jteOutput.writeContent("\n\n    <div class=\"space-y-4 mt-3\" id=\"comment-card-container\">\n        ")
		for (comment in post.comments) {
			jteOutput.writeContent("\n            ")
			gg.jte.generated.ondemand.component.comment.JtecommentCardViewGenerated.render(jteOutput, jteHtmlInterceptor, comment, userId);
			jteOutput.writeContent("\n        ")
		}
		jteOutput.writeContent("\n    </div>\n</div>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val post = params["post"] as freeapp.life.stella.api.web.dto.PostDetailDto
		val userId = params["userId"] as Long?
		render(jteOutput, jteHtmlInterceptor, post, userId);
	}
}
}
