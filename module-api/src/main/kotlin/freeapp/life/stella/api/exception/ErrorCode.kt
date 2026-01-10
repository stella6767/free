package freeapp.life.stella.api.exception

enum class ErrorCode(
    val code: Int,
    val msg: String,
) {

    Unauthorized(401, "토큰 만료"),

}