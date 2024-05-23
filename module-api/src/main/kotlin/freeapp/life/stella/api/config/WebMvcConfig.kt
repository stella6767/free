package freeapp.life.stella.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig (
    private val mapper: ObjectMapper
) : WebMvcConfigurer {

    private val log = KotlinLogging.logger {  }

//    override fun addInterceptors(registry: InterceptorRegistry) {
//
//        registry
//            .addInterceptor(FileRemoveInterceptor())
//            .addPathPatterns("/ts/url")
//    }

}