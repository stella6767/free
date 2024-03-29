package com.stella.free.global.config

import com.stella.free.global.util.logger
import com.stella.free.global.property.AwsProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.*
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client



@Configuration
@EnableConfigurationProperties(AwsProperty::class)
class AwsConfig(
    private val awsProperty: AwsProperty,
) {

    private val log = logger()


    @Bean
    fun createAwsCredential(): AwsCredentials {
        return object : AwsCredentials {
            override fun accessKeyId(): String {
                return awsProperty.credentials.accessKey
            }

            override fun secretAccessKey(): String {
                return awsProperty.credentials.secretKey
            }
        }
    }


    @Bean //일단 직접 구현체로 등록
    fun amazonS3Client(awsCreds: AwsCredentials): S3Client {

        println("accessKey=>${awsProperty.credentials.accessKey}")

        return S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .build()
    }







}