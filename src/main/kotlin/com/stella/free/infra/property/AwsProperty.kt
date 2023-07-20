package com.stella.free.infra.property

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "cloud.aws")
class AwsProperty(
    val credentials: Credentials,
    val s3: S3,
){


    data class Credentials(
        val accessKey: String,
        val secretKey: String
    )


    data class S3(
        val bucket: String,
        val url: String,
    )
}