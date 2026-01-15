package freeapp.life.stella.api.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.transfer.s3.S3TransferManager


@Configuration
class S3Config {


    @Value("\${cloud.aws.s3.accessKey}")
    private lateinit var accessKey:String

    @Value("\${cloud.aws.s3.secretKey}")
    private lateinit var secretKey:String


    fun createAwsCredential(): StaticCredentialsProvider {

        return StaticCredentialsProvider
            .create(AwsBasicCredentials.create(accessKey, secretKey))
    }


    @Bean
    fun amazonS3Client(): S3Client {

        return S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(createAwsCredential())
            .build()
    }

    /**
     * S3transfer manager
     * 대용량 파일 업로드 전용
     * @return
     */
    @Bean
    fun s3TransferManager(): S3TransferManager {

        val s3AsyncClient = S3AsyncClient.crtBuilder()
            .credentialsProvider(createAwsCredential())
            .region(Region.AP_NORTHEAST_2)
            .targetThroughputInGbps(20.0)
            .minimumPartSizeInBytes(8 * 1024 * 1024) // 8MB
            .maxConcurrency(20)
            .checksumValidationEnabled(true)
            .build()

        return S3TransferManager.builder()
            .s3Client(s3AsyncClient)
            .build()
    }


    @Bean
    fun s3PreSigner(): S3Presigner {

        val s3Configuration = S3Configuration.builder()
            .accelerateModeEnabled(true)
            .build()

        return S3Presigner
            .builder()
            .credentialsProvider(createAwsCredential())
            .serviceConfiguration(s3Configuration)
            .region(Region.AP_NORTHEAST_2)
            .build()

    }
}
