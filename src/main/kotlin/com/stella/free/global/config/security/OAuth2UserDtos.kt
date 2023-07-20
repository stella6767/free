package com.stella.free.global.config.security

import com.stella.free.core.account.entity.type.SignType


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


data class FaceBookAuth2UserInfo(
    override val attributes: Map<String, Any?>,
    override val signType: SignType = SignType.FACEBOOK,
) : OAuth2UserInfo {

    override fun getUsername(): String {
        return attributes.get("id").toString()
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

    override fun getUsername(): String {
        return attributes.get("id").toString()
    }
    override fun getSocialPictureUrl(): String? {
        return attributes.get("avatar_url")?.toString()
    }
    override fun getEmail(): String? {
        return attributes.get("email")?.toString()
    }
}