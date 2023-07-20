package com.stella.free.infra.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding




@ConfigurationProperties(prefix = "cloud.aws")
class AwsProperty(
    val credentials: Credentials,
    val s3: S3,
    val region:Region
){


    data class Credentials(
        val accessKey: String,
        val secretKey: String
    )


    data class S3(
        val bucket: String,
        val url: String,
    )


    data class Region(
        val static: String,
    )
}