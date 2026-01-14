package freeapp.life.stella.api.web.dto

data class InitialUploadReqDto(
    val filename: String,
) {


}

data class InitialUploadDto(
    val uploadId: String,
    val fileKey: String,
)


data class S3UploadSignedUrlDto(
    val fileKey: String,
    val uploadId: String,
    val partNumber: Int
)


data class S3UploadSignedUrlResDto(
    val partNumber: Int,
    val preSignedUrl: String,
)


data class S3UploadResultDto(
    val fileKey: String,
    val name: String,
    val size: Long,
    val fileUrl: String,
    val isRequest: Boolean,
) {


}


data class S3UploadCompleteDto(
    val taskId: Long,
    val uploadId: String,
    val fileKey: String,
    val isRequest: Boolean,
    val parts: List<S3UploadPartsDetailDto> = mutableListOf()
) {

}

data class S3UploadPartsDetailDto(
    val awsETag: String,
    val partNumber: Int
)


data class S3UploadAbortDto(
    val uploadId: String,
    val filename: String
)

data class PresignedURLDto(
    val url: String,
)


