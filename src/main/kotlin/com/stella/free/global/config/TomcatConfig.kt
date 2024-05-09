package com.stella.free.global.config

import org.apache.coyote.ProtocolHandler
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.core.task.TaskDecorator
import org.springframework.core.task.support.TaskExecutorAdapter
import java.util.concurrent.Executors


@Configuration
class TomcatConfig {


    // Async Task에 Virtual Thread 사용

}