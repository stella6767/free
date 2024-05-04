package com.stella.free.web.page.scraper

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors


@ViewComponent
class TSDownloaderViewComponent {

    fun render(): ViewContext {

        val resource =
            ClassPathResource("static/README.md").getInputStream()

        val introduction =
            BufferedReader(InputStreamReader(resource)).use { reader ->
                reader.lines().collect(Collectors.joining("\n"))
            }



        return ViewContext(

        )
    }
}