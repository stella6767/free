package com.stella.free.global.config.filter

import com.stella.free.global.util.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.util.StringUtils
import org.springframework.web.servlet.HandlerInterceptor
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class FileRemoveInterceptor(

) : HandlerInterceptor {

    private val log = logger()
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val fileName = response.getHeader("tmpName")

        if (StringUtils.hasLength(fileName)){
            val decodeFilename =
                URLDecoder.decode(fileName, StandardCharsets.UTF_8.toString())
            val file = File(decodeFilename)
            if (file.delete()) {
                log.info("파일이 삭제되었습니다.")
            } else {
                log.warn ( "파일이 삭제되지 못했습니다. ==> ${file.absolutePath}" )
            }
        }
    }
}