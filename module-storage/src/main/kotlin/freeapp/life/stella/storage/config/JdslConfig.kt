package freeapp.life.stella.storage.config

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackages = ["freeapp.life.stella"])
@EnableJpaRepositories(basePackages = ["freeapp.life.stella"])
class JdslConfig {

    @Bean
    fun jpqlRenderContext(): JpqlRenderContext {
        return JpqlRenderContext()
    }
    @Bean
    fun jpqlRenderer(): JpqlRenderer {
        return JpqlRenderer()
    }


}