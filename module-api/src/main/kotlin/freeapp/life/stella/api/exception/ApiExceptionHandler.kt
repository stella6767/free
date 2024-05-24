package freeapp.life.stella.api.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@RestControllerAdvice
class ApiExceptionHandler(

) {


    private val log = KotlinLogging.logger {  }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.error("handleMethodArgumentNotValidException", e)
        val response: ErrorResponse = ErrorResponse.of(e.localizedMessage , e.getBindingResult())
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        log.error("handleBindException", e)
        val response: ErrorResponse = ErrorResponse.of(e.localizedMessage, e.getBindingResult())
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        log.error("handleMethodArgumentTypeMismatchException", e)
        val response: ErrorResponse = ErrorResponse.of(e.localizedMessage, null)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        log.error("handleHttpRequestMethodNotSupportedException", e)
        val response: ErrorResponse = ErrorResponse.of(e.localizedMessage, null)
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorResponse?> {
        log.error("handleAccessDeniedException", e)
        val response: ErrorResponse = ErrorResponse.of(e.localizedMessage, null)
        return ResponseEntity(response, HttpStatus.NOT_ACCEPTABLE)
    }


//    @ExceptionHandler(BusinessException::class)
//    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse?> {
//        log.error("handleEntityNotFoundException", e)
//        val errorCode: ErrorCode = e.getErrorCode()
//        val response: ErrorResponse = ErrorResponse.of(errorCode)
//        return ResponseEntity<Any?>(response, HttpStatus.valueOf(errorCode.getStatus()))
//    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("handleException", e)

        val response: ErrorResponse = ErrorResponse.of(e.localizedMessage, null)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}