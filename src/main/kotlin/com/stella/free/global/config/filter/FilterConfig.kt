package com.stella.free.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.global.util.logger
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.filter.ServletContextRequestLoggingFilter


@Configuration
class FilterConfig(
    private val mapper:ObjectMapper
) {

    private val log = logger()

    //@Bean
    fun commonsRequestLoggingFilter(): CommonsRequestLoggingFilter {
        val filter = CommonsRequestLoggingFilter()

        filter.setIncludeClientInfo(true)
        filter.setIncludeHeaders(true)
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(1000)
        filter.setBeforeMessagePrefix("### REQUEST_LOGGING : ")

        return filter
    }

    @Bean
    fun servletContextRequestLoggingFilter(): ServletContextRequestLoggingFilter {
        return CustomServletContextRequestLoggingFilter()
    }


    @Bean
    fun mdcLoggingFilterRegister(): FilterRegistrationBean<MDCLoggingFilter> {
        val bean: FilterRegistrationBean<MDCLoggingFilter> =
            FilterRegistrationBean(MDCLoggingFilter())
        bean.addUrlPatterns("/*")
        bean.order = Ordered.HIGHEST_PRECEDENCE //필터 순서.. 낮은 숫자가 먼저 실행됨
        return bean
    }



    //@Bean
//    fun exceptionHandlerFilterRegister(): FilterRegistrationBean<ExceptionHandlerFilter> {
//        val bean: FilterRegistrationBean<ExceptionHandlerFilter> =
//            FilterRegistrationBean(ExceptionHandlerFilter(mapper))
//        bean.addUrlPatterns("/*")
//        bean.order = -5000//필터 순서.. 낮은 숫자가 먼저 실행됨
//        return bean
//    }

}