package freeapp.life.stella.api.service.file


import freeapp.life.stella.api.util.generateRandomNumberString
import freeapp.life.stella.api.web.dto.DownloadDto
import freeapp.life.stella.api.web.dto.S3UploadPartsDetailDto

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest
import java.io.FileNotFoundException
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration


@Service
class S3Service(
    private val appS3Client: S3Client,
    private val s3PreSigner: S3Presigner
) {

    private val log = KotlinLogging.logger { }


    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var appBucket: String

    @Value("s3://[S3_BUCKET_NAME]/[FILE_NAME]")
    private val s3Resource: Resource? = null

    @Value("\${cloud.aws.cloudFront.url}")
    private lateinit var staticUrl: String


    private val appS3Utilities = appS3Client.utilities()

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
            .bucket(appBucket)
            .key(key)
            .contentType(multipartFile.contentType)
            .build()

        val objectResponse = appS3Client.putObject(
            objectRequest,
            RequestBody.fromInputStream(multipartFile.inputStream, multipartFile.size)
        )

        val getUrlRequest = GetUrlRequest.builder()
            .bucket(appBucket)
            .key(key)
            .build()

        val url = appS3Utilities.getUrl(getUrlRequest)
        log.debug { "url is $url" }
        return staticUrl + key
    }


    fun createS3Client(
        accessKey: String,
        secretKey: String,
        region: String,
        endPoint: String
    ): S3Client {

        log.debug { "createS3Client endPoint===>$endPoint region===>$region" }

        val credentials =
            AwsBasicCredentials.create(accessKey, secretKey)

        val clientBuilder = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))

        return if (endPoint.isNotBlank()) {
            clientBuilder
                .endpointOverride(URI.create(endPoint))
                .build()
        } else clientBuilder.build()
    }


    fun getDownloadPresignedUrl(
        preSigner: S3Presigner,
        bucket: String,
        fileKey: String,
    ): DownloadDto {

        val filename = fileKey.substringAfterLast('/')
        val encodedFileName =
            URLEncoder.encode(filename, StandardCharsets.UTF_8.toString())

        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(fileKey)
            .responseContentDisposition("attachment; filename=\"$encodedFileName\"")
            .build()

        val presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(30))
            .getObjectRequest(getObjectRequest)
            .build()

        val presignedUrl =
            preSigner.presignGetObject(presignRequest)

        val urlString = presignedUrl.url().toString()

        val downloadDto = DownloadDto(
            url = urlString,
            filename = encodedFileName
        )

        return downloadDto
    }


    fun initiateMultipartUpload(
        client: S3Client,
        bucket: String,
        fileKey: String,
        contentType: String,
    ): String {

        val createMultipartUploadRequest =
            CreateMultipartUploadRequest.builder()
                .bucket(bucket) // 버킷 설정
                .key(fileKey) // 업로드될 경로 설정
                .contentType(contentType)
                .build()

        // Amazon S3는 멀티파트 업로드에 대한 고유 식별자인 업로드 ID가 포함된 응답을 반환합니다.
        val response =
            client.createMultipartUpload(createMultipartUploadRequest)

        return response.uploadId()
    }


    fun getPresignedPartUrl(
        signer: S3Presigner,
        bucket: String,
        fileKey: String,
        uploadId: String,
        partNumber: Int,
    ): String {

        val uploadPartRequest =
            UploadPartRequest.builder()
                .bucket(bucket)
                .key(fileKey)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build()

        // 미리 서명된 URL 요청
        // connection pool 문제를 어떻게 해결해야할까..
        val uploadPartPresignedRequest =
            UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .uploadPartRequest(uploadPartRequest)
                .build()

        // 클라이언트에서 S3로 직접 업로드하기 위해 사용할 인증된 URL을 받는다.
        val presignedUploadPartRequest =
            signer.presignUploadPart(uploadPartPresignedRequest)

        return presignedUploadPartRequest.url().toString()
    }


    fun getSingleUploadUrl(
        s3PreSigner: S3Presigner,
        bucket: String,
        fileKey: String,
        contentType: String
    ): String {

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(fileKey)
            .contentType(contentType)
            .build()

        val preSignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(30)) // The URL will expire in  minutes.
            .putObjectRequest(putObjectRequest)
            .build()

        val uploadSignedUrl =
            s3PreSigner.presignPutObject(preSignRequest).url().toString()

        return uploadSignedUrl
    }


    fun createS3Presigner(
        accessKey: String,
        secretKey: String,
        region: String,
        endPoint: String
    ): S3Presigner {

        val credentials =
            AwsBasicCredentials.create(accessKey, secretKey)

        val builder = S3Presigner
            .builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))

        log.debug { "createS3Presigner endPoint===>$endPoint region===>$region" }

        return if (endPoint.isNotBlank()) {
            builder.endpointOverride(URI.create(endPoint)).region(Region.of(region)).build()
        } else builder.endpointOverride(URI.create(endPoint)).region(Region.of(region)).build()
    }


    fun completeUpload(
        client: S3Client,
        bucket: String,
        uploadId: String,
        fileKey: String,
        parts: List<S3UploadPartsDetailDto>,
    ): String {

        val completedParts: MutableList<CompletedPart> = ArrayList()

        // 모든 한 영상에 대한 모든 부분들에 부분 번호와 Etag를 설정함
        for (partForm in parts) {
            val part = CompletedPart.builder()
                .partNumber(partForm.partNumber)
                .eTag(partForm.awsETag)
                .build()
            completedParts.add(part)
        }

        // 멀티파트 업로드 완료 요청을 AWS 서버에 보냄
        val completedMultipartUpload =
            CompletedMultipartUpload.builder().parts(completedParts).build()

        val fileKey = fileKey

        val completeMultipartUploadRequest =
            CompleteMultipartUploadRequest.builder()
                .bucket(bucket) // 버킷 설정
                .key(fileKey)
                .uploadId(uploadId) // 업로드 아이디
                .multipartUpload(completedMultipartUpload) // 영상의 모든 부분 번호, Etag
                .build()

        val completeMultipartUploadResponse =
            client.completeMultipartUpload(completeMultipartUploadRequest)
        val objectKey = completeMultipartUploadResponse.key()
        val bucket = completeMultipartUploadResponse.bucket()

        return fileKey
    }


    fun abortMultipartUpload(
        client: S3Client,
        bucket: String,
        filename: String,
        uploadId: String,
    ) {

        val abortMultipartUploadRequest =
            AbortMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(filename)
                .uploadId(uploadId)
                .build()

        log.debug { "abort uploadID===>" + uploadId }

        try {
            client.abortMultipartUpload(abortMultipartUploadRequest)
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
            appS3Client.getObject(getObjectRequest)

        return resp.response().contentLength()
    }


}
