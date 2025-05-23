@file:Suppress("ktlint")
package gg.jte.generated.ondemand.component.post
import org.springframework.data.domain.Page
import freeapp.life.stella.api.web.dto.PostCardDto
import org.springframework.util.StringUtils // Assuming this is for StringUtils.hasText
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class JtepostViewGenerated {
companion object {
	@JvmField val JTE_NAME = "component/post/postView.kte"
	@JvmField val JTE_LINE_INFO = intArrayOf(0,0,0,1,2,4,4,4,4,4,8,8,8,11,11,11,14,14,14,22,22,24,24,26,26,26,26,26,26,26,26,29,29,30,30,31,31,33,33,35,35,36,36,37,37,39,39,41,41,41,4,5,5,5,5,5)
	@JvmStatic fun render(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, keyword:String, posts:Page<PostCardDto>) {
		jteOutput.writeContent("\n<div id=\"posts-container\" class=\"py-5\">\n    ")
		if (StringUtils.hasText(keyword)) {
			jteOutput.writeContent("\n        <div class=\"container m-auto\">\n            <div class=\"flex justify-center\">\n                <h1 class=\"text-xl font-bold\">")
			jteOutput.setContext("h1", null)
			jteOutput.writeUserContent(keyword)
			jteOutput.writeContent("의 검색결과</h1>\n            </div>\n            <div class=\"flex justify-center\">\n                총 <span class=\"italic\">")
			jteOutput.setContext("span", null)
			jteOutput.writeUserContent(posts.totalElements)
			jteOutput.writeContent("</span> 개의 포스트를 찾았습니다.\n            </div>\n            <div class=\"flex justify-center mt-3\">\n                <button class=\"btn btn-wide\" onclick=\"location.href='/blog'\">\n                    전체보기\n                </button>\n            </div>\n        </div>\n    ")
		}
		jteOutput.writeContent("\n\n    ")
		if (!posts.isLast) {
			jteOutput.writeContent("\n        <div class=\"container m-auto grid grid-cols-4 gap-4\"\n             hx-get=\"/posts?page=")
			jteOutput.setContext("div", "hx-get")
			jteOutput.writeUserContent(posts.pageable.pageNumber + 1)
			jteOutput.setContext("div", null)
			jteOutput.writeContent("&keyword=")
			jteOutput.setContext("div", "hx-get")
			jteOutput.writeUserContent(keyword)
			jteOutput.setContext("div", null)
			jteOutput.writeContent("\"\n             hx-trigger=\"revealed\"\n             hx-swap=\"afterend\">\n            ")
			for (post in posts.content) {
				jteOutput.writeContent("\n                ")
				gg.jte.generated.ondemand.component.post.JtepostCardViewGenerated.render(jteOutput, jteHtmlInterceptor, post);
				jteOutput.writeContent("\n            ")
			}
			jteOutput.writeContent("\n        </div>\n    ")
		} else {
			jteOutput.writeContent("\n        <div class=\"container m-auto grid grid-cols-4 gap-4\">\n            ")
			for (post in posts.content) {
				jteOutput.writeContent("\n                ")
				gg.jte.generated.ondemand.component.post.JtepostCardViewGenerated.render(jteOutput, jteHtmlInterceptor, post);
				jteOutput.writeContent("\n            ")
			}
			jteOutput.writeContent("\n        </div>\n    ")
		}
		jteOutput.writeContent("\n</div>\n")
	}
	@JvmStatic fun renderMap(jteOutput:gg.jte.html.HtmlTemplateOutput, jteHtmlInterceptor:gg.jte.html.HtmlInterceptor?, params:Map<String, Any?>) {
		val keyword = params["keyword"] as String
		val posts = params["posts"] as Page<PostCardDto>
		render(jteOutput, jteHtmlInterceptor, keyword, posts);
	}
}
}
