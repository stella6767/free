package freeapp.life.stella.api.web.dto

import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.type.SignType


data class UserResponseDto(
    val username: String,
) {
    companion object {

        fun fromEntity(user: User): UserResponseDto {
            return UserResponseDto(
                user.username,
            )
        }
    }

}


interface OAuth2UserInfo {

    val attributes: Map<String, Any?>

    val signType: SignType

    fun getUsername(): String

    fun getSocialPictureUrl(): String?

    fun getEmail(): String?

}


data class GoogleAuth2UserInfo(
    override val attributes: Map<String, Any?>,
    override val signType: SignType = SignType.GOOGLE,
) : OAuth2UserInfo {

    override fun getUsername(): String {
        return attributes.get("sub").toString()
    }
    override fun getSocialPictureUrl(): String? {
        return attributes.get("picture")?.toString()
    }
    override fun getEmail(): String? {
        return attributes.get("email")?.toString()
    }
}


//data class FaceBookAuth2UserInfo(
//    override val attributes: Map<String, Any?>,
//    override val signType: SignType = SignType.FACEBOOK,
//) : OAuth2UserInfo {
//
//    override fun getUsername(): String {
//        return attributes.get("id").toString()
//    }
//    override fun getSocialPictureUrl(): String? {
//        return attributes.get("picture")?.toString()
//    }
//    override fun getEmail(): String? {
//        return attributes.get("email")?.toString()
//    }
//}
//

data class GithubAuth2UserInfo(
    override val attributes: Map<String, Any?>,
    override val signType: SignType = SignType.GITHUB,
) : OAuth2UserInfo {

    override fun getUsername(): String {
        return attributes["id"].toString()
    }
    override fun getSocialPictureUrl(): String? {
        return attributes["avatar_url"]?.toString()
    }
    override fun getEmail(): String? {
        return attributes["email"]?.toString()
    }
}
