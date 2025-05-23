@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.post
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostCardViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/post/postCardView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,0,0,0,0,3,3,3,3,3,4,4,4,4,7,7,7,7,7,7,7,7,7,11,11,11,12,12,12,15,15,15,0,0,0,0,0)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, post:freeapp.life.stella.api.web.dto.PostCardDto) {
		jteOutput.writeContent("\n<div class=\"card mt-5 bg-base-100 shadow-xl hover:bg-cyan-600 cursor-pointer\"\n     id=\"post-card-")
		jteOutput.setContext("div", "id")
		jteOutput.writeUserContent(post.id)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("\"\n     onclick=\"location.href='/page/post/")
		jteOutput.setContext("div", "onclick")
		jteOutput.writeUserContent(post.id)
		jteOutput.setContext("div", null)
		jteOutput.writeContent("'\">\n    <figure class=\"bg-slate-300\">\n        <div>\n            <img")
		val __jte_html_attribute_0 = post.thumbnail
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" src=\"")
			jteOutput.setContext("img", "src")
			jteOutput.writeUserContent(__jte_html_attribute_0)
			jteOutput.setContext("img", null)
			jteOutput.writeContent("\"")
		}
		jteOutput.writeContent(" alt=\"post-thumbnail\" />\n        </div>\n    </figure>\n    <div class=\"card-body h-32\">\n        <h2 class=\"card-title\">")
		jteOutput.setContext("h2", null)
		jteOutput.writeUserContent(post.title)
		jteOutput.writeContent("</h2>\n        <p class=\"truncate\">")
		jteOutput.setContext("p", null)
		jteOutput.writeUserContent(post.thumbnailContent)
		jteOutput.writeContent("</p>\n    </div>\n</div>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val post = params["post"] as freeapp.life.stella.api.web.dto.PostCardDto
		render(jteOutput, jteHtmlInterceptor, post);
	}
}
}
