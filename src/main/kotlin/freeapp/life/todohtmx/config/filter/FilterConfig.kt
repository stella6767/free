package freeapp.life.todohtmx.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
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

    private val log = KotlinLogging.logger {  }

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


}