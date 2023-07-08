package com.stella.free.exception

import com.stella.free.util.ScriptUtil
import com.stella.free.util.logger
import com.stella.free.view.component.toast.ToastViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@ControllerAdvice
class ExceptionHandler(
    private val toastViewComponent: ToastViewComponent,
) {

    private val log = logger()

    @ExceptionHandler(AppException::class)
    fun handleMyAppException(exception: AppException): ViewContext {

        val pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value())
        pd.detail = exception.localizedMessage

        return toastViewComponent.render(
            exception.message!!,
            10000
        )
    }

    @ExceptionHandler(*arrayOf(AuthenticationException::class, AccessDeniedException::class))
    @ResponseBody
    fun handleAuthException(exception: Exception): String {

        log.error(exception.message)

        val pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED.value())
        pd.detail = exception.localizedMessage

        log.info("????")

        return ScriptUtil.alertError(pd.detail!!)

//        return toastViewComponent.render(
//            pd.detail!!,
//            10000
//        )
    }


    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: AppException): ViewContext {

        log.error(exception.message)

        return toastViewComponent.render(
            exception.message!!,
            10000
        )
    }


}