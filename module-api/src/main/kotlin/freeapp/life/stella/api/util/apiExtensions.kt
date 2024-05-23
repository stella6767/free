package freeapp.life.stella.api.util

import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
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

