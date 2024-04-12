package com.stella.free.setup

import com.fasterxml.jackson.databind.ObjectMapper

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer


import com.p6spy.engine.spy.P6SpyOptions
import com.stella.free.core.blog.repo.CommentRepository
import com.stella.free.core.blog.repo.CommentRepositoryImpl

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
    fun loggingConfig(): LoggingConfig {

        return LoggingConfig()
    }

    @Bean
    fun jpqlRenderContext(): JpqlRenderContext {

        return JpqlRenderContext()
    }

    @Bean
    fun jpqlRenderer(): JpqlRenderer {
        return JpqlRenderer()
    }



    @Bean
    fun objectMapper(): ObjectMapper {

        return JacksonConfig().objectMapper()
    }


    @Bean
    fun commentRepository(): CommentRepository {

        return CommentRepositoryImpl(jpqlRenderer(), jpqlRenderContext(), entityManager)
    }



}