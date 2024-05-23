package freeapp.life.stella.api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
class EncoderConfig {

    @Bean
    fun encode(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }



}