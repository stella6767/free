package freeapp.life.stella.api.web.dto

import org.springframework.web.multipart.MultipartFile


enum class QrGeneratorType(
    val icon: String,
    val fieldName: String,
) {

    LINK("\uD83D\uDD17", "Link"),
    TEXT("\uD83D\uDCC4 ", "Text"),
    WIFI( "\uD83D\uDEDC", "Wifi"),
    VCARD("🪪", "V-Card"),
    TEL("\uD83D\uDCDE", "Tel"),
    PDF("\uD83D\uDCDC", "PDF"),

}




data class TextReqDto(
    val text:String,
)

data class VCardReqDto(
    val firstName: String,
    val lastName: String,
    val phoneNumber:String,
)


data class WifiReqDto(
    val ssid: String,
    val encryption: String,
    val password:String,
)


data class CallReqDto(
    val countryCode: String,
    val phoneNumber:String,
)


