package freeapp.life.stella.api.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.JdkClientHttpRequestFactory
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Duration
import java.util.concurrent.Executors


@Configuration
class RestClientConfig(

) {

    private val log = KotlinLogging.logger {  }

    private val velogUrl = "https://v2cdn.velog.io/graphql"
    private val publicApisUrl = "https://api.publicapis.org/"
    private val githubUrl = "https://api.github.com"



    @Bean
    fun gitHubClient(): RestClient {

        val requestFactory = getRequestFactory()

        return RestClient
            .builder()
            .baseUrl(githubUrl)
            .requestFactory(requestFactory)
            .defaultStatusHandler(RestClientErrorHandler())
            .requestInterceptor(RestTemplateLoggingInterceptor())
            .build()

    }


    @Bean
    fun basicClient(): WebClient {

        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(-1)
            } // to unlimited memory size
            .build()

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build()
    }

    @Bean
    fun velogClient(): WebClient {
        //https://github.com/davemachado/public-api


        return WebClient.builder()
            .baseUrl(velogUrl)
            .filter(logRequest())  // logging the request headers
            .filter(logResponse())  // logging the response headers
            .clientConnector(ReactorClientHttpConnector(createReactorHttpClient()))
            .codecs { configurer ->
                configurer
                    .defaultCodecs()
                    .maxInMemorySize(500 * 1024 * 1024)
            }
            .build()
    }


    @Bean
    fun publicApiClient(): WebClient {
        //https://github.com/davemachado/public-api


        return WebClient.builder()
            .baseUrl(publicApisUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filter(addContentTypeHeader())
            .filter(logRequest())  // logging the request headers
            .filter(logResponse())  // logging the response headers
            .clientConnector(ReactorClientHttpConnector(createReactorHttpClient()))
            .codecs { configurer ->
                configurer
                    .defaultCodecs()
                    .maxInMemorySize(500 * 1024 * 1024)
            }
            .build()
    }


    private fun logBody(response: ClientResponse): Mono<ClientResponse> {

        return if (response.statusCode() != null && (response.statusCode().is4xxClientError || response.statusCode().is5xxServerError)) {
            response.bodyToMono(String::class.java)
                .flatMap { body ->
                    log.error("Error Body is {}", body)
                    //Mono.error(MyCustomClientException())
                    Mono.just(response)
                }
        } else {
            Mono.just<ClientResponse>(response)
        }
    }


    fun addContentTypeHeader(): ExchangeFilterFunction {
        return ExchangeFilterFunction { clientRequest: ClientRequest, next: ExchangeFunction ->
            next.exchange(
                ClientRequest.from(clientRequest!!)
                    .headers { headers -> headers.contentType = MediaType.APPLICATION_JSON_UTF8 }
                    .build()
            )
        }
    }


    fun logRequest(): ExchangeFilterFunction {

        return ExchangeFilterFunction.ofRequestProcessor { clientRequest: ClientRequest ->

            log.info(
                """
                =========== Request ===========

                Request: ${clientRequest.method()} : ${clientRequest.url()}
                ${clientRequest.headers().map { it.key + " : " + it.value }}

                =========== Request ===========
               """.trimIndent()
            )
            Mono.just(clientRequest)
        }
    }

    fun logResponse(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
            log.info(
                """
                =========== response ===========

                Response status: ${clientResponse.statusCode()}
                ${clientResponse.headers().asHttpHeaders().map { it.key + " : " + it.value }}

                =========== response ===========
               """.trimIndent()
            )

            logBody(clientResponse)
        }
    }

    fun createReactorHttpClient(): HttpClient {


        val provider: ConnectionProvider = ConnectionProvider.builder("custom-provider")
            .maxConnections(100) //유지할 Connection Pool의 수
            .maxIdleTime(Duration.ofSeconds(120)) //maxIdleTime : 사용하지 않는 상태(idle)의 Connection이 유지되는 시간.
            .maxLifeTime(Duration.ofSeconds(58)) //maxLifeTime : Connection Pool 에서의 최대 수명 시간
            .pendingAcquireTimeout(Duration.ofMillis(5000))  // Connection Pool에서 사용할 수 있는 Connection 이 없을때 (모두 사용중일때) Connection을 얻기 위해 대기하는 시간
            .pendingAcquireMaxCount(-1) // Connection을 얻기 위해 대기하는 최대 수 -1: 무제한
            .evictInBackground(Duration.ofSeconds(30))  //백그라운드에서 만료된 connection을 제거하는 주기
            .lifo() //  마지막에 사용된 커넥션을 재 사용, fifo – 처음 사용된(가장오래된) 커넥션을 재 사용
            .metrics(true) //connection pool 사용 정보를 actuator metric에 노출
            .build()


        return HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Duration.ofSeconds(120).toMillis().toInt())
            .responseTimeout(Duration.ofSeconds(30))
            .compress(true)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(1000))
                conn.addHandlerLast(WriteTimeoutHandler(30))
            }
    }


    private fun getRequestFactory(): JdkClientHttpRequestFactory {

        val requestFactory =
            JdkClientHttpRequestFactory(
                java.net.http.HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build()
            )

        requestFactory.setReadTimeout(Duration.ofSeconds(5))
        return requestFactory
    }


    class RestTemplateLoggingInterceptor : ClientHttpRequestInterceptor {

        val log = KotlinLogging.logger{}

        override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {

            log.info {
                """
                    
            =====Request======
            Headers: ${request.headers}    
            Request Method:${request.method}
            Request URI: ${request.uri}            
            =====Request======
            """.trimIndent()
            }

            return execution.execute(request, body)
        }

    }


    class RestClientErrorHandler : ResponseErrorHandler {

        private val log = KotlinLogging.logger {}

        override fun hasError(response: ClientHttpResponse): Boolean {

            val statusCode = response.statusCode
            //    response.getBody() 넘겨 받은 body 값으로 적절한 예외 상태 확인 이후 boolean return
            return !statusCode.is2xxSuccessful
        }

        override fun handleError(response: ClientHttpResponse) {
            val error = getErrorAsString(response)

            log.error {
                """
                                        
                ================
                Headers: ${response.headers}    
                Response Status: ${response.statusCode}           
                error: ${error}
                ================
            """.trimIndent()
            }

            //error: ${error}
        }

        private fun getErrorAsString(response: ClientHttpResponse): String {
            BufferedReader(InputStreamReader(response.body)).use { br -> return br.readLine() }
        }
    }

}
