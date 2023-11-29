package com.stella.free.global.config.filter

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.util.StringUtils
import org.springframework.web.filter.ServletContextRequestLoggingFilter
import java.util.*

class CustomServletContextRequestLoggingFilter(


) : ServletContextRequestLoggingFilter() {

    init {
        isIncludeHeaders = true
        isIncludeClientInfo = true
        isIncludePayload = true
        isIncludeQueryString = true
        maxPayloadLength = 1000
    }

    override fun shouldLog(request: HttpServletRequest): Boolean {
        //val requestURI = request.requestURI
        //return !requestURI.startsWith("/exclude-path")
        //user-agent:"ELB-HealthChecker/2.0"
        val userAgent = request.getHeader("User-Agent")

        return !(userAgent.contains("ELB-HealthChecker") || userAgent.contains("Prometheus"))
    }

//    override fun beforeRequest(request: HttpServletRequest, message: String) {
//
//        super.beforeRequest(request, message)
//    }


    override fun createMessage(request: HttpServletRequest, prefix: String, suffix: String): String {

        val url = if (isIncludeQueryString){
            var requestURI = request.requestURI
            val queryString = request?.queryString
            if (queryString != null){
                requestURI += "?$queryString"

            }
            requestURI
        }else request.requestURI

        val headers = if (isIncludeHeaders) {
            val httpHeaders = ServletServerHttpRequest(request).getHeaders()
            if (headerPredicate != null) {
                val names = request.headerNames
                while (names.hasMoreElements()) {
                    val header = names.nextElement()
                    if (!headerPredicate!!.test(header)) {
                        httpHeaders[header] = "masked"
                    }
                }
            }
            httpHeaders.toString()
        }else ""


        val clientInfoTotal = if (isIncludeClientInfo) {
            var clientInfo = ""
            if (StringUtils.hasLength(request.remoteAddr)) {
                clientInfo += " , client=${request.remoteAddr} "
            }
            if (request.getSession(false) != null) {
                clientInfo += " , session=${request.getSession(false).id} "
            }
            if (request.remoteUser != null) {
                clientInfo += " , user=${request.remoteUser} "
            }
            clientInfo
        }else ""


        val payload = if (isIncludePayload) {
            val payloadData = getMessagePayload(request)
            payloadData ?: ""
        }else ""


        return  """
            
            HTTP Method: ${request.method}
            url: ${url} 
            clientInfo: ${clientInfoTotal}
            headers: ${headers}
            payload: $payload
                      
        """.trimIndent()
    }
}