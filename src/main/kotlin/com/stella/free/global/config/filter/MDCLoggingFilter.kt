package com.stella.free.global.config.filter


import com.stella.free.global.util.logger
import org.slf4j.MDC
import java.util.*
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse


class MDCLoggingFilter(

) : Filter {

    private val log = logger()
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        //log.debug { "MDC가 제일 먼저 실행됨 " }

        val uuid = UUID.randomUUID()
        MDC.put("request_id", uuid.toString());
        response.characterEncoding = "utf-8"
        chain.doFilter(request, response)
        MDC.clear()
    }


}