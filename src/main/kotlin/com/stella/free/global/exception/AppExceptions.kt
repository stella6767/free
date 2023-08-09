package com.stella.free.global.exception


sealed class AppException(message: String = "") : RuntimeException(message)

class UnSupportedSocialException(message: String) : AppException(message)

class CustomAuthenticationException(message: String) : AppException(message)
class PostNotFoundException(message: String = "target post not found") : AppException(message)
