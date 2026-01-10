package freeapp.life.stella.api.config.filter

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.filter.ServletContextRequestLoggingFilter

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
        val requestURI = request.requestURI
        //return !requestURI.startsWith("/exclude-path")
        //user-agent:"ELB-HealthChecker/2.0"

        val staticExts = listOf(
            ".css", ".js", ".map",
            ".png", ".jpg", ".jpeg", ".gif", ".svg", ".ico",
            ".woff", ".woff2", ".ttf", ".eot",
        )

        return staticExts.none { requestURI.contains(it, ignoreCase = true) }
    }



    override fun createMessage(request: HttpServletRequest, prefix: String, suffix: String): String {

        val url = if (isIncludeQueryString){
            var requestURI = request.requestURI
            val queryString = request?.queryString
            if (queryString != null){
                requestURI += "?$queryString"
            }
            requestURI
        }else request.requestURI

        println("request.queryString=>${request.queryString}")


        val payload = if (isIncludePayload) {
            val payloadData = getMessagePayload(request)
            payloadData ?: ""
        }else ""


        return  """
            
            HTTP Method: ${request.method}
            url: ${url} 
            payload: $payload
                      
        """.trimIndent()
    }
}