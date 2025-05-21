package freeapp.life.stella.api.exception

import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

data class ErrorResponse(
    val message: String,
    val code: Int,
    var errors: List<FieldError> = ArrayList(),
) {
    companion object {
        fun createByErrorCode(
            errorCode: ErrorCode
        ): ErrorResponse {

            return ErrorResponse(
                message = errorCode.msg,
                code = errorCode.code
            )

        }

        fun of(message: String?, bindingResult: BindingResult?): ErrorResponse {

            return ErrorResponse(
                message = message ?: "",
                code = 500,
                errors = bindingResult?.fieldErrors ?: listOf()
            )
        }

    }

}
