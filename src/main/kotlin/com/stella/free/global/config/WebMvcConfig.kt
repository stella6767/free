package com.stella.free.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.global.config.filter.FileRemoveInterceptor
import com.stella.free.global.util.logger
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig (
    private val mapper: ObjectMapper
) : WebMvcConfigurer {

    private val log = logger()

//    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
//        super.configureMessageConverters(converters)
//        val messageConverter = MappingJackson2HttpMessageConverter()
//        messageConverter.objectMapper = mapper
//        converters.add(messageConverter)
//    }

    override fun addInterceptors(registry: InterceptorRegistry) {

        registry
            .addInterceptor(FileRemoveInterceptor())
            .addPathPatterns("/ts/url")
    }

}