package freeapp.life.stella.api.config.security


import freeapp.life.stella.api.service.sign.OAuth2SignService
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    private val userRepository: UserRepository,
) {


    private val log = KotlinLogging.logger {}

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            val arrays = arrayOf(
                AntPathRequestMatcher("/resources/**"),
                AntPathRequestMatcher("/static/**"),
                AntPathRequestMatcher("/img/**"),
                AntPathRequestMatcher("/css/**"),
                AntPathRequestMatcher("/js/**")
            )
            web.ignoring()
                .requestMatchers(*arrays)
        }
    }


    @Bean
    fun filterChain(
        http: HttpSecurity,
        encoder: PasswordEncoder,
    ): SecurityFilterChain {

        val authorizePattern =
            arrayOf("/asdasdsad")


        http.csrf { csrf -> csrf.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .headers {
                it.frameOptions { config ->
                    config.sameOrigin()
                }
            }
            .cors {
                it.configurationSource(corsConfigurationSource())
            }


        http
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(*authorizePattern)
                    .authenticated()
                    .anyRequest()
                    .permitAll()
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .userInfoEndpoint { endpoint ->
                        endpoint.userService(OAuth2SignService(userRepository, encoder))
                    }
                    .successHandler(CustomLoginSuccessHandler(userRepository))
                    .failureHandler { request, response, exception ->
                        log.error(exception.message, exception)
                        response.sendRedirect("/auth/sign")
                    }
                    .permitAll()
            }
            .logout {
                it.logoutRequestMatcher(AntPathRequestMatcher("/logout", "GET"))
                it.logoutSuccessHandler(CustomLogoutSuccessHandler())
                it.invalidateHttpSession(true)
                it.clearAuthentication(true)
                it.deleteCookies("JSESSIONID")
                it.permitAll()
            }
            .exceptionHandling {
                it.accessDeniedHandler(WebAccessDeniedHandler()) // 권한이 없는 사용자 접근 시
                it.authenticationEntryPoint(WebAuthenticationEntryPoint()) //인증되지 않는 사용자 접근 시
            }

        return http.build()
    }


    class WebAccessDeniedHandler : AccessDeniedHandler {
        override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            accessDeniedException: AccessDeniedException
        ) {
            throw accessDeniedException
        }
    }


    class WebAuthenticationEntryPoint(
    ) : AuthenticationEntryPoint {

        private val log = KotlinLogging.logger { }

        override fun commence(
            request: HttpServletRequest, response: HttpServletResponse,
            authException: AuthenticationException
        ) {

            log.debug("Access denied, redirecting to index page")
            // 인증되지 않은 경우 페이지 이동 시 사용
            response.sendRedirect("/")
            // 인증되지 않은 경우 에러코드 반환 시 사용
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

    class CustomLogoutSuccessHandler : LogoutSuccessHandler {

        private val log = KotlinLogging.logger { }

        override fun onLogoutSuccess(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication?
        ) {

            log.info("logout success")

            val context = SecurityContextHolder.getContext()
            context.authentication = null
            SecurityContextHolder.clearContext()

            response.sendRedirect("/")
        }

    }


    class CustomLoginSuccessHandler(
        private val userRepository: UserRepository,
    ) : AuthenticationSuccessHandler {

        private val log = KotlinLogging.logger { }

        override fun onAuthenticationSuccess(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication
        ) {


            val principal = authentication.principal as UserPrincipal
//            val SESSION_TIMEOUT_IN_SECONDS = 60 * 120 //단위는 초, 2시간 간격으로 세션만료
//            request.session.maxInactiveInterval = SESSION_TIMEOUT_IN_SECONDS //세션만료시간.
            SecurityContextHolder.getContext().authentication = authentication

            principal.user.updateLastLoginDate()
            userRepository.save(principal.user)

            log.info("login success, ${request.requestURI} ")

            if (principal.user.role == User.Role.USER) {
                response.sendRedirect("/")
            }

        }
    }


    class OauthLoginSuccessHandler : AuthenticationSuccessHandler {

        private val log = KotlinLogging.logger { }

        override fun onAuthenticationSuccess(
            request: HttpServletRequest, response: HttpServletResponse,
            authentication: Authentication
        ) {
//            val userDetails = authentication.principal as UserDetails
//            val SESSION_TIMEOUT_IN_SECONDS = 60 * 120 //단위는 초, 2시간 간격으로 세션만료
//            request.session.maxInactiveInterval = SESSION_TIMEOUT_IN_SECONDS //세션만료시간.
            request.session.maxInactiveInterval = 3600
            SecurityContextHolder.getContext().authentication = authentication

            log.info("login success, ${request.requestURI}   ${request.requestURL}")
            //response.writer.write("")
            response.sendRedirect("/")
        }
    }


    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
        configuration.allowedHeaders = listOf("*")
        configuration.exposedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }


}
