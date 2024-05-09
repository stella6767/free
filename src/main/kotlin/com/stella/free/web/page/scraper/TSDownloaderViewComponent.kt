package com.stella.free.web.page.scraper

import com.stella.free.global.util.getMarkdownValueFormLocal
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable
import java.util.stream.Collectors


@ViewComponent
class TSDownloaderViewComponent {

    fun render(): ViewContext {

        val markdownValue =
            ClassPathResource("static/TSDownloder-README.md").getMarkdownValueFormLocal()

        return ViewContext(
            "introduction" toProperty markdownValue,
        )

    }


}