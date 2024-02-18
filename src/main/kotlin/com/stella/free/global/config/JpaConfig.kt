package com.stella.free.global.config



import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
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


    @Bean
    fun jpqlRenderContext(): JpqlRenderContext {
        return JpqlRenderContext()
    }

    @Bean
    fun jpqlRenderer(): JpqlRenderer {
        return JpqlRenderer()
    }



}
