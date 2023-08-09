package com.stella.free.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.global.util.ScriptUtil
import com.stella.free.global.util.logger
import com.stella.free.web.component.toast.ToastViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class ExceptionHandler(
    private val toastViewComponent: ToastViewComponent,
    private val objectMapper: ObjectMapper
) {

    private val log = logger()

//    @ExceptionHandler(AppException::class)
//    fun handleMyAppException(exception: AppException): ViewContext {
//
//        val pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value())
//        pd.detail = exception.localizedMessage
//
//        return toastViewComponent.render(
//            exception.message!!,
//            10000
//        )
//    }


    @ExceptionHandler(AppException::class)
    fun handleMyAppException(exception: AppException): ResponseEntity<String> {

        log.error(exception.message)

        val status = HttpStatus.UNAUTHORIZED
        val pd = ProblemDetail.forStatus(status.value())
        pd.detail = exception.message

        return ResponseEntity(objectMapper.writeValueAsString(pd), status)
    }


    @ExceptionHandler(*arrayOf(AuthenticationException::class, AccessDeniedException::class))
    @ResponseBody
    fun handleAuthException(exception: Exception): String {

        log.error(exception.message)

        val pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED.value())
        pd.detail = exception.localizedMessage

        val jsonErrorString = objectMapper.writeValueAsString(pd.detail!!)

        return ScriptUtil.alertErrorAndHistoryBack(jsonErrorString)
    }


    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<String> {

        log.error(exception.localizedMessage)

        val status = HttpStatus.UNAUTHORIZED
        val pd = ProblemDetail.forStatus(status.value())
        pd.detail = exception.localizedMessage

        return ResponseEntity(objectMapper.writeValueAsString(pd), status)
    }


}