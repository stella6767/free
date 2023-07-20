package com.stella.free.setup

import com.fasterxml.jackson.databind.ObjectMapper

import com.p6spy.engine.spy.P6SpyOptions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.stella.free.global.config.JacksonConfig
import com.stella.free.global.config.LoggingConfig
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean


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