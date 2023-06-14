package com.example.simpletodo.exception

import com.example.simpletodo.util.logger
import com.example.simpletodo.view.component.toast.ToastViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.Problemdetails
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

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


    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: AppException): ViewContext {

        log.error(exception.message)

        return toastViewComponent.render(
            exception.message!!,
            10000
        )
    }


}