package com.stella.free.setup

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.addSerializer

import com.p6spy.engine.spy.P6SpyOptions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.stella.free.config.JacksonConfig
import com.stella.free.config.LoggingConfig
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime



@TestConfiguration
internal class RepositoriesTestConfig {

    @PersistenceContext
    private lateinit var entityManager: EntityManager


    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        P6SpyOptions.getActiveInstance().logMessageFormat = LoggingConfig.P6spyPrettySqlFormatter::class.java.name
        return JPAQueryFactory(entityManager)
    }


    @Bean
    fun objectMapper(): ObjectMapper {

        return JacksonConfig().objectMapper()
    }


}