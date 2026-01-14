package freeapp.life.stella.api.web.dto

import freeapp.life.stella.api.util.EMAIL_PATTERN
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.type.SignType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile


data class SignUpDto(
    @field:NotBlank(message = "email is required")
    @field:Email(regexp = EMAIL_PATTERN, message = "It's invalid email format")
    val email: String,
    val password: String
) {

    fun toEntity(encPassword: String): User {
        return User(
            email = email,
            username = "",
            password = encPassword,
            signType = SignType.EMAIL,
            status = User.Status.PENDING,
            rawData = ""
        )
    }

}

data class LoginDto(
    val email: String,
    val password: String
)

data class VerifyDto(
    val code: String,
    val token: String,
)

data class ResendCodeDto(
    val email: String,
    val token: String,
)

data class SignUpResponseDto(
    val email: String,
    val expireMinute: Long,
    val token: String,
)


data class UserDeleteRequestDto(
    val reason: String,
)



data class UserResponseDto(
    val username: String,
    val email: String,
    val role: User.Role,
    val status: User.Status,
    val signType: SignType,
    val profileImg: String
) {
    companion object {

        fun fromEntity(user: User): UserResponseDto {
            return UserResponseDto(
                user.username,
                user.email ?: "",
                user.role,
                user.status,
                user.signType,
                user.profileImg
            )
        }
    }

}


data class UpdateProfileDto(
    val password: String = "",
    val username: String,
)



interface OAuth2UserInfo {

    val attributes: Map<String, Any?>

    val signType: SignType


    fun getSocialId(): String

    fun getSocialPictureUrl(): String?

    fun getEmail(): String?

}


data class GoogleAuth2UserInfo(
    override val attributes: Map<String, Any?>,
    override val signType: SignType = SignType.GOOGLE,
) : OAuth2UserInfo {


    override fun getSocialId(): String {
        return attributes.get("sub").toString()
    }

    override fun getSocialPictureUrl(): String? {
        return attributes.get("picture")?.toString()
    }
    override fun getEmail(): String? {
        return attributes.get("email")?.toString()
    }
}



data class GithubAuth2UserInfo(
    override val attributes: Map<String, Any?>,
    override val signType: SignType = SignType.GITHUB,
) : OAuth2UserInfo {


    override fun getSocialId(): String {
        return attributes["id"].toString()
    }

    override fun getSocialPictureUrl(): String? {
        return attributes["avatar_url"]?.toString()
    }
    override fun getEmail(): String? {
        return attributes["email"]?.toString()
    }
}
