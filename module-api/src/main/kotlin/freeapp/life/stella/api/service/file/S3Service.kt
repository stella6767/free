package freeapp.life.stella.api.service.file


import freeapp.life.stella.api.util.customDelimiter
import freeapp.life.stella.api.util.generateRandomNumberString
import freeapp.life.stella.api.web.dto.InitialUploadDto
import freeapp.life.stella.api.web.dto.S3UploadAbortDto
import freeapp.life.stella.api.web.dto.S3UploadCompleteDto
import freeapp.life.stella.api.web.dto.S3UploadResultDto
import freeapp.life.stella.api.web.dto.S3UploadSignedUrlDto
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest
import java.io.File
import java.io.FileNotFoundException
import java.time.Duration


@Service
class S3Service(
    private val s3Client: S3Client,
    private val s3PreSigner: S3Presigner
) {

    private val log = KotlinLogging.logger { }


    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    @Value("s3://[S3_BUCKET_NAME]/[FILE_NAME]")
    private val s3Resource: Resource? = null

//    @Value("\${s3.url}")
//    private lateinit var staticUrl: String

    private val s3Utilities = s3Client.utilities()

    fun putObject(
        multipartFile: MultipartFile,
        folderName: String
    ): String {

        if (multipartFile.isEmpty) {
            throw FileNotFoundException()
        }

        log.debug { "file name is ${multipartFile.originalFilename}" }

        val fileName =
            generateRandomNumberString() + "_" + multipartFile.originalFilename

        val key = folderName + "/" + fileName

        val objectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(multipartFile.contentType)
            .build()

        val objectResponse = s3Client.putObject(
            objectRequest,
            RequestBody.fromInputStream(multipartFile.inputStream, multipartFile.size)
        )

        val getUrlRequest = GetUrlRequest.builder()
            .bucket(bucket)
            .key(key)
            .build()

        val url = s3Utilities.getUrl(getUrlRequest)

        log.debug { "url is $url" }

        return url.toString()
    }


    fun initiateUpload(
        dirname: String,
        filename: String
    ): InitialUploadDto {

        val fileKey = dirname + File.separator + filename

        val createMultipartUploadRequest =
            CreateMultipartUploadRequest.builder()
                .bucket(bucket) // 버킷 설정
                .key(fileKey) // 업로드될 경로 설정
                .build()
        // Amazon S3는 멀티파트 업로드에 대한 고유 식별자인 업로드 ID가 포함된 응답을 반환합니다.
        val response =
            s3Client.createMultipartUpload(createMultipartUploadRequest)

        return InitialUploadDto(response.uploadId(), fileKey)
    }


    fun getUploadSignedUrl(
        s3UploadSignedUrlDto: S3UploadSignedUrlDto,
    ): String {

        val uploadPartRequest =
            UploadPartRequest.builder()
                .bucket(bucket)
                .key(s3UploadSignedUrlDto.fileKey)
                .uploadId(s3UploadSignedUrlDto.uploadId)
                .partNumber(s3UploadSignedUrlDto.partNumber)
                .build()

        // 미리 서명된 URL 요청
        // todo connection pool 문제를 어떻게 해결해야할까..
        val uploadPartPresignRequest =
            UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .uploadPartRequest(uploadPartRequest)
                .build()

        // 클라이언트에서 S3로 직접 업로드하기 위해 사용할 인증된 URL을 받는다.
        val presignedUploadPartRequest =
            s3PreSigner.presignUploadPart(uploadPartPresignRequest)

        return presignedUploadPartRequest.url().toString()
    }

    fun completeUpload(
        s3UploadCompleteDto: S3UploadCompleteDto,
    ): S3UploadResultDto {

        val completedParts: MutableList<CompletedPart> = ArrayList()

        // 모든 한 영상에 대한 모든 부분들에 부분 번호와 Etag를 설정함
        for (partForm in s3UploadCompleteDto.parts) {
            val part = CompletedPart.builder()
                .partNumber(partForm.partNumber)
                .eTag(partForm.awsETag)
                .build()
            completedParts.add(part)
        }

        // 멀티파트 업로드 완료 요청을 AWS 서버에 보냄
        val completedMultipartUpload =
            CompletedMultipartUpload.builder().parts(completedParts).build()

        val fileKey = s3UploadCompleteDto.fileKey

        val completeMultipartUploadRequest =
            CompleteMultipartUploadRequest.builder()
                .bucket(bucket) // 버킷 설정
                .key(fileKey)
                .uploadId(s3UploadCompleteDto.uploadId) // 업로드 아이디
                .multipartUpload(completedMultipartUpload) // 영상의 모든 부분 번호, Etag
                .build()

        val completeMultipartUploadResponse =
            s3Client.completeMultipartUpload(completeMultipartUploadRequest)
        val objectKey = completeMultipartUploadResponse.key()
        val bucket = completeMultipartUploadResponse.bucket()
        val fileSize = getFileSizeFromS3Url(bucket, objectKey)

        return S3UploadResultDto(
            name = fileKey.split(customDelimiter).last(),
            fileKey = fileKey,
            size = fileSize,
            //fileUrl = "${GlobalProperties.staticUrl}/$fileKey",
            fileUrl = "",
            isRequest = s3UploadCompleteDto.isRequest,
        )
    }


    fun abortUpload(
        s3UploadAbortDto: S3UploadAbortDto,
    ) {

        val abortMultipartUploadRequest =
            AbortMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(s3UploadAbortDto.filename)
                .uploadId(s3UploadAbortDto.uploadId)
                .build()

        log.info { "abort uploadID===>" + s3UploadAbortDto.uploadId }

        try {
            s3Client.abortMultipartUpload(abortMultipartUploadRequest)
        } catch (e: NoSuchUploadException) {
            log.error { e.localizedMessage }
        }
    }


    private fun getFileSizeFromS3Url(bucketName: String, fileName: String): Long {

        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        val resp =
            s3Client.getObject(getObjectRequest)

        return resp.response().contentLength()
    }


}
