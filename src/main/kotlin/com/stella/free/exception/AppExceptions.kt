package com.stella.free.exception


sealed class AppException(message: String = "") : RuntimeException(message)

class UnSupportedSocialException(message: String) : AppException(message)

class CustomAuthenticationException(message: String) : AppException(message)
