@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.comment
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtecommentCardViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/comment/commentCardView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,3,3,3,3,3,4,4,4,4,9,9,9,10,10,10,11,11,11,11,12,12,13,13,13,14,14,15,15,15,19,19,19,19,22,22,23,23,23,23,26,26,29,29,31,31,31,31,34,34,34,34,38,38,44,44,44,44,45,45,49,49,50,50,50,50,51,51,53,53,54,54,54,0,1,1,1,1,1)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, comment:freeapp.life.stella.api.web.dto.CommentCardDto, userId:Long?) {
		jteOutput.writeContent("\n<div id=\"comment-card-")
		jteOutput.setContext("div", "id")
		jteOutput.writeUserContent(comment.commentId)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("\">\n    <div class=\"flex mt-3\" id=\"comment-card-")
		jteOutput.setContext("div", "id")
		jteOutput.writeUserContent(comment.commentId)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("\">\n        <div class=\"text-black font-bold\">\n\n        </div>\n        <div class=\"flex-1 border rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed\">\n            <strong class=\"text-black\">")
		jteOutput.setContext("strong", null)
		jteOutput.writeUserContent(comment.username)
		jteOutput.writeContent("</strong>\n            <span class=\"text-xs text-black ml-2\">")
		jteOutput.setContext("span", null)
		jteOutput.writeUserContent(comment.createdAt)
		jteOutput.writeContent("</span>\n            <p class=\"text-sm text-black\" id=\"comment-content-")
		jteOutput.setContext("p", "id")
		jteOutput.writeUserContent(comment.commentId)
		jteOutput.setContext("p", null)
		jteOutput.writeContent("\">\n                ")
		if (comment.parentCommentUsername.isNotEmpty()) {
			jteOutput.writeContent("\n                    <span class=\"truncate text-sm text-gray\">@")
			jteOutput.setContext("span", null)
			jteOutput.writeUserContent(comment.parentCommentUsername)
			jteOutput.writeContent("  </span>\n                ")
		}
		jteOutput.writeContent("\n                ")
		jteOutput.setContext("p", null)
		jteOutput.writeUserContent(comment.content)
		jteOutput.writeContent("\n            </p>\n            <div class=\"mt-4 flex items-center justify-between\">\n                <div class=\"flex -space-x-2 mr-2\">\n                    <button class=\"text-right text-blue-500 mr-10\" hx-on--click=\"htmx.toggleClass('#comment-reply-form-")
		jteOutput.setContext("button", "hx-on--click")
		jteOutput.writeUserContent(comment.commentId)
		jteOutput.setContext("button", null)
		jteOutput.writeContent("', 'hidden')\">\n                        Reply\n                    </button>\n                    ")
		if (comment.childComments.isNotEmpty()) {
			jteOutput.writeContent("\n                        <button hx-on--click=\"htmx.toggleClass('#reply-comment-card-")
			jteOutput.setContext("button", "hx-on--click")
			jteOutput.writeUserContent(comment.commentId)
			jteOutput.setContext("button", null)
			jteOutput.writeContent("', 'hidden')\" class=\"text-right text-blue-500 ml-5\">\n                            show reply\n                        </button>\n                    ")
		}
		jteOutput.writeContent("\n                </div>\n                <div>\n                    ")
		if (comment.userId == userId) {
			jteOutput.writeContent("\n                        <button class=\"btn btn-link delete-comment\"\n                                hx-delete=\"/comment/")
			jteOutput.setContext("button", "hx-delete")
			jteOutput.writeUserContent(comment.commentId)
			jteOutput.setContext("button", null)
			jteOutput.writeContent("\"\n                                hx-confirm=\"Are you sure you wish to delete your comment?\"\n                                hx-trigger=\"click\"\n                                hx-target=\"#comment-card-")
			jteOutput.setContext("button", "hx-target")
			jteOutput.writeUserContent(comment.commentId)
			jteOutput.setContext("button", null)
			jteOutput.writeContent("\"\n                                hx-swap=\"delete\">\n                            X\n                        </button>\n                    ")
		}
		jteOutput.writeContent("\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"hidden\" id=\"comment-reply-form-")
		jteOutput.setContext("div", "id")
		jteOutput.writeUserContent(comment.commentId)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("\">\n        ")
		gg.jte.generated.ondemand.component.comment.JtecommentFormViewGenerated.render(jteOutput, jteHtmlInterceptor, userId, comment.postId, comment.commentId);
		jteOutput.writeContent("\n    </div>\n</div>\n\n")
		for (childComment in comment.childComments) {
			jteOutput.writeContent("\n    <div class=\"hidden\" id=\"reply-comment-card-")
			jteOutput.setContext("div", "id")
			jteOutput.writeUserContent(comment.commentId)
			jteOutput.setContext("div", null)
			jteOutput.writeContent("\">\n        ")
			gg.jte.generated.ondemand.component.comment.JtecommentCardViewGenerated.render(jteOutput, jteHtmlInterceptor, childComment, userId);
			jteOutput.writeContent("\n    </div>\n")
		}
		jteOutput.writeContent("\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val comment = params["comment"] as freeapp.life.stella.api.web.dto.CommentCardDto
		val userId = params["userId"] as Long?
		render(jteOutput, jteHtmlInterceptor, comment, userId);
	}
}
}
