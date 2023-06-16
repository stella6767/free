package com.stella.free.entity.type

import com.fasterxml.jackson.annotation.JsonCreator

enum class SignType(
    val clientName: String
) {
    //CommonOAuth2Provider

    FACEBOOK("Facebook"),
    GOOGLE("Google"),
    GITHUB("GitHub"),

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