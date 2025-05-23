package freeapp.life.stella.api.util


import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.web.dto.UserResponseDto
import org.springframework.security.core.context.SecurityContextHolder

fun getCurrentUser(): UserResponseDto? {
    try {
        val auth =
            SecurityContextHolder.getContext().authentication

        if (auth != null && auth.isAuthenticated &&
            auth.principal != "anonymousUser" &&
            auth.principal is UserPrincipal
        ) {
            println("User not found")
            val userPrincipal = auth.principal as UserPrincipal
            return UserResponseDto.fromEntity(userPrincipal.user)
        }

    } catch (e: Exception) {
        println("사용자 정보 가져오기 실패: ${e.message}")
    }
    return null
}

fun isLoggedIn(): Boolean {

    return getCurrentUser() != null
}


fun clearSecurityContext() {
    val context =
        SecurityContextHolder.getContext()
    context.authentication = null
    SecurityContextHolder.clearContext()
}

