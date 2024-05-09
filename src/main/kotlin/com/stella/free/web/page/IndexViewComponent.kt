package com.stella.free.web.page

import com.stella.free.global.util.getMarkdownValueFormLocal
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors


@ViewComponent
class IndexViewComponent(

) {

    private val log = logger()

    fun render(): ViewContext {

        val markdownValue =
            ClassPathResource("static/README.md").getMarkdownValueFormLocal()


        return ViewContext(
            "introduction" toProperty markdownValue,
        )

    }



}