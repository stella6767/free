package com.stella.free.global.config

import com.stella.free.global.service.FileUploader
import com.stella.free.global.service.S3FileUploaderImpl
import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import software.amazon.awssdk.services.s3.S3Client


@Configuration
class GlobalServiceBeanConfig {

    @Value("\${spring.profiles.active:unknown}")
    private val profile: String? = null


    @Bean
    fun fileUploader(s3Client: S3Client): FileUploader {
        //if (profile == "local")



        return S3FileUploaderImpl(s3Client)
    }



}