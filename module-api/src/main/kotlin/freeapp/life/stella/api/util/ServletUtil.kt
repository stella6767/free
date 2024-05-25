package freeapp.life.stella.api.util

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object ServletUtil {

    private val IP_HEADERS = arrayOf(
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    )


    fun getCurrentRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }


    fun getCurrentResponse(): HttpServletResponse? {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response
    }


    fun getRequestHeader(key:String): String {
        return getCurrentRequest().getHeader(key) ?: ""
    }



    fun getRequestIP(request: HttpServletRequest): String {

        for (ipHeader in IP_HEADERS) {
            val value = request.getHeader(ipHeader)

            if (value == null || value.isEmpty()) continue

            val parts = value.split("\\s*,\\s*")
            return parts.first()
        }

        return request.remoteAddr
    }


}