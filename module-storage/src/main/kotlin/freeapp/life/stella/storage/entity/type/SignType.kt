package freeapp.life.stella.storage.entity.type

import com.fasterxml.jackson.annotation.JsonCreator

enum class SignType(
    val clientName: String,
    val authorizationUrl:String,
    val imgUrl:String,
) {
    //CommonOAuth2Provider

//    FACEBOOK("Facebook",
//        "/oauth2/authorization/facebook", "" +
//                "https://upload.wikimedia.org/wikipedia/en/0/04/Facebook_f_logo_%282021%29.svg"),

    GOOGLE("Google",
        "/oauth2/authorization/google",
        "/img/google-mark.svg"),

    GITHUB("GitHub",
        "/oauth2/authorization/github",
        "/img/github-mark.png"),

    ;

    companion object {
        @JsonCreator
        fun from(str: String): SignType? {
            return SignType.values().firstOrNull {
                it.name == str.uppercase()
            }
        }
    }
}
