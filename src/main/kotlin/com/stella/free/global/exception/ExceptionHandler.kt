package com.stella.free.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.global.util.ScriptUtil
import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.toMap
import gg.jte.TemplateEngine
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler(
    private val templateEngine: TemplateEngine,
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
    fun handleMyAppException(exception: AppException): String {

        log.error(exception.localizedMessage)

        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val pd = ProblemDetail.forStatus(status.value())
        pd.detail = exception.localizedMessage

        //ResponseEntity(objectMapper.writeValueAsString(pd), status)

        return ScriptUtil.alertErrorAndHistoryBack(objectMapper.writeValueAsString(pd.detail!!))

    }


    @ExceptionHandler(*arrayOf(AuthenticationException::class, AccessDeniedException::class, EntityNotFoundException::class))
    fun handleAuthException(exception: Exception): String {

        log.error(exception.message)

        val pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED.value())
        pd.detail = exception.localizedMessage

        val jsonErrorString = objectMapper.writeValueAsString(pd.detail!!)

        return ScriptUtil.alertErrorAndHistoryBack(jsonErrorString)
    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): String {

        val detail =
            createProblemDetail(HttpStatus.BAD_REQUEST, e.localizedMessage)

        log.error(e.message)

        return objectMapper.writeValueAsString(detail)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): String? {

        val detail =
            createProblemDetail(HttpStatus.BAD_REQUEST, e.localizedMessage)

        return objectMapper.writeValueAsString(detail)
    }





    private fun createProblemDetail(status: HttpStatus, message:String): ProblemDetail {
        val pd = ProblemDetail.forStatus(status.value())
        pd.detail = message
        return pd
    }





}