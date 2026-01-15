package freeapp.life.stella.api.exception

import freeapp.life.stella.api.util.ServletUtil
import gg.jte.TemplateEngine
import gg.jte.TemplateOutput
import gg.jte.output.StringOutput
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap
import jakarta.servlet.http.HttpServletResponse

import mu.KotlinLogging
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.ui.Model
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.server.ResponseStatusException


@ControllerAdvice
class GlobalExceptionHandler(

) {

    private val log = KotlinLogging.logger { }

    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception,
        htmxResponse: HtmxResponse,
        htmxRequest: HtmxRequest,
        response: HttpServletResponse,
        model: Model,
    ): String {

        if (!ex.localizedMessage.contains(".well-known/appspecific/com.chrome.devtools")) {
            log.error("handleException", ex)
        }

        val status =
            if (ex is ResponseStatusException) {
                ex.statusCode
            } else {
                AnnotationUtils.findAnnotation(ex.javaClass, ResponseStatus::class.java)?.value
                    ?: HttpStatus.BAD_REQUEST
            }

        response.status = status.value()
        htmxResponse.reswap = HtmxReswap.innerHtml()
        htmxResponse.retarget = "#error-alert-container"

        if (htmxRequest.isHtmxRequest) {
            htmxResponse.pushUrl = htmxRequest.currentUrl
        }

        if (!htmxRequest.isHtmxRequest) {
            model.addAttribute("isHtmxRequest", false)
        }

        model.addAttribute("msg", ex.message)

        return "component/util/alertView"
    }

}
