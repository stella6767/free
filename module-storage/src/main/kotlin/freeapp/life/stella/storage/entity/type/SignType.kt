package freeapp.life.stella.storage.entity.type

import com.fasterxml.jackson.annotation.JsonCreator

enum class SignType(
    val clientName: String,
    val authorizationUrl: String,
    val imgUrl: String,
) {
    //CommonOAuth2Provider

    EMAIL(
        "Email",
        "/oauth2/authorization/email",
        ""
    ),

//    GOOGLE(
//        "Google",
//        "/oauth2/authorization/google",
//        "/img/icon/google-mark.svg"
//    ),

    GITHUB(
        "GitHub",
        "/oauth2/authorization/github",
        "/img/icon/github-mark.png"
    ),

    ;

    companion object {
        @JsonCreator
        fun from(str: String): SignType? {
            return SignType.entries.firstOrNull {
                it.name == str.uppercase()
            }
        }

        fun getSocialTypes(): List<SignType> {
            return SignType.entries.filter { !it.clientName.contains("Email") }
        }

    }
}
