package freeapp.life.stella.api.service.file


import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Utilities
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.FileNotFoundException
import java.util.*

class S3FileUploaderImpl(
    private val amazonS3: S3Client,
) : FileUploader {

    private val log = KotlinLogging.logger {  }

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String = ""

    @Value("s3://[S3_BUCKET_NAME]/[FILE_NAME]")
    private val s3Resource: Resource? = null


    private val s3Utilities: S3Utilities = amazonS3.utilities()

    override fun upload(multipartFile: MultipartFile): String {


        if (multipartFile.isEmpty) {
            throw FileNotFoundException()
        }

        log.info(multipartFile.originalFilename)

        val uuid = UUID.randomUUID().toString()
        val fileName = uuid + "_" + multipartFile.originalFilename

        val objectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(fileName)
            .contentType(multipartFile.contentType)
            .build()

        val objectResponse = amazonS3.putObject(
            objectRequest,
            RequestBody.fromInputStream(multipartFile.inputStream, multipartFile.size)
        )

        val getUrlRequest = GetUrlRequest.builder()
            .bucket(bucket).key(fileName)
            .build()

        val url = s3Utilities.getUrl(getUrlRequest)

        return url.toString()
    }
}