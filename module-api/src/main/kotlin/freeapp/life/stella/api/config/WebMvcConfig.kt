package freeapp.life.stella.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import freeapp.life.stella.api.service.file.FileUploader
import freeapp.life.stella.api.service.file.S3FileUploaderImpl
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class WebMvcConfig (
    private val mapper: ObjectMapper
) : WebMvcConfigurer {

    private val log = KotlinLogging.logger {  }


    @Value("\${spring.profiles.active:unknown}")
    private val profile: String? = null

//    override fun addInterceptors(registry: InterceptorRegistry) {
//
//        registry
//            .addInterceptor(FileRemoveInterceptor())
//            .addPathPatterns("/ts/url")
//    }




    @Bean
    fun fileUploader(s3Client: S3Client): FileUploader {
        //if (profile == "local")
        return S3FileUploaderImpl(s3Client)
    }


}