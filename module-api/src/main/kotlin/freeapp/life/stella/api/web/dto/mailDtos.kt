package freeapp.life.stella.api.web.dto

data class EmailDto(
    val emailTemplate: EmailTemplate,
    val body: EmailBodyDto,
)

data class EmailBodyDto(
    val verificationCode: String = "",
    val validityMinutes: String = "",
)


enum class EmailTemplate(
    val templateTarget: String,
    val subject: String,
    val desc: String,
) {

    VERIFICATION(
        "mail/verification.kte",
        "freeapp verification Code",
        "인증 코드",
    ),

}
