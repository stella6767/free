package com.stella.free.setup

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl

import com.p6spy.engine.spy.P6SpyOptions

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


//    @Bean
//    fun springDataQueryFactory(): SpringDataQueryFactory {
//        P6SpyOptions.getActiveInstance().logMessageFormat = P6SpyLoggingConfig.P6spyPrettySqlFormatter::class.java.getName()
//        return SpringDataQueryFactoryImpl(CriteriaQueryCreatorImpl(entityManager), SubqueryCreatorImpl())
//    }

    @Bean
    fun objectMapper(): ObjectMapper {

        return JacksonConfig().objectMapper()
    }


}