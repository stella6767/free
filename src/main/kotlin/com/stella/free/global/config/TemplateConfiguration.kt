package com.stella.free.global.config

import com.stella.free.global.util.logger
import gg.jte.CodeResolver
import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.resolve.DirectoryCodeResolver
import gg.jte.springframework.boot.autoconfigure.JteViewResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Path
import java.nio.file.Paths

@Configuration
class TemplateConfiguration {

    @Value("\${spring.profiles.active:unknown}")
    private val profile: String? = null

    private val log = logger()

    @Bean
    fun jteViewResolver(templateEngine: TemplateEngine): JteViewResolver {
        //.kte 아직 불완전..
        return JteViewResolver(templateEngine, ".kte").apply {
            //this.setSuffix(".kte")
        }
    }



    @Bean
    fun templateEngine(): TemplateEngine {

        //val profile = System.getProperty("spring.profiles.active")

        println("profile = $profile")

        return if ("prod" == profile) {
            log.info("preCompiled")
            // Templates will be compiled by the maven build task
            TemplateEngine.createPrecompiled(ContentType.Html)
        } else {
//            val split = jteProperties.templateLocation.split("/".toRegex()).dropLastWhile { it.isEmpty() }
//                .toTypedArray()
//            val codeResolver: CodeResolver = DirectoryCodeResolver(FileSystems.getDefault().getPath("", *split))

            val codeResolver: CodeResolver = DirectoryCodeResolver(Path.of("src", "main", "kotlin"))

            val templateEngine =
                TemplateEngine.create(codeResolver, Paths.get("jte-classes"), ContentType.Html, javaClass.classLoader)
            templateEngine.setBinaryStaticContent(true)
            templateEngine
        }
    }

}