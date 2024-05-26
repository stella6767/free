package freeapp.life.stella.api.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern
import java.util.stream.Collectors

fun ClassPathResource.getMarkdownValueFormLocal(): String {

    val resource = this.inputStream
    val introduction =
        BufferedReader(InputStreamReader(resource)).use { reader ->
            reader.lines().collect(Collectors.joining("\n"))
        }

    val parser = Parser.builder().build()
    val document = parser.parse(introduction)
    val renderer = HtmlRenderer.builder().build()

    return renderer.render(document)
}


fun List<String>.toJson(): String {

    return if (this.isEmpty() || this == null) "[]" else ObjectMapper().writeValueAsString(this)
}


fun Document.toClosedTagHtml(): String {

    for (element in this.select("img, array, br, dependencies, area, base, col, embed, hr, input, link, meta, param, source, track, wbr")) {
        if (element.tagName() == "img" || element.tagName() == "br") {
            convertToSelfClosing(element)
        }
    }
    val cleanedHtml: String = this.body().html().replace("&lt;", "<")
        .replace("&gt;", ">")

    return cleanedHtml
}


private fun convertToSelfClosing(element: Element) {
    val tag: StringBuilder = StringBuilder("<").append(element.tagName())
    element.attributes().forEach { attr ->
        tag.append(" ").append(attr.key).append("=\"").append(attr.value).append("\"")
    }
    tag.append(" />")
    //println(tag.toString())
    element.replaceWith(TextNode(tag.toString()))
}


inline fun String.removeSpecialCharacters(): String {
    // 정규표현식 패턴: 숫자 및 문자를 제외한 모든 특수 문자 및 공백 (A-Za-z0-9는 숫자와 문자)
    val pattern = "[^A-Za-z0-9]"
    val p = Pattern.compile(pattern)
    val m = p.matcher(this)
    // 매칭되는 패턴을 빈 문자열로 치환하여 제거
    return m.replaceAll("")
}