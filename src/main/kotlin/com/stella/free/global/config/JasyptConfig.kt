package com.stella.free.global.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class JasyptConfig(

) {

    @Value("\${jasypt.encryptor.password}")
    private lateinit var PASSWORD: String

    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.password = PASSWORD
        config.setPoolSize("1")
        config.algorithm = "PBEWithMD5AndDES"
        config.stringOutputType = "base64"
        config.setKeyObtentionIterations("1000")
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        encryptor.setConfig(config)
        return encryptor
    }



}