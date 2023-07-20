package com.stella.free.global.config



import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


@Configuration
@EnableJpaAuditing
class JpaConfig(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @PersistenceContext
    private val entityManager: EntityManager
) {

//    @Bean
//    fun jpaQueryFactory(): JPAQueryFactory {
//        return JPAQueryFactory(entityManager)
//    }




}
