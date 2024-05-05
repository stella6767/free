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

    // Web Request 를 처리하는 Tomcat 이 Virtual Thread를 사용하여 유입된 요청을 처리하도록 한다.
    @Bean
    fun protocolHandlerVirtualThreadExecutorCustomizer(): TomcatProtocolHandlerCustomizer<*> {
        return TomcatProtocolHandlerCustomizer { protocolHandler: ProtocolHandler ->
            protocolHandler.executor = Executors.newVirtualThreadPerTaskExecutor()
        }
    }

    // Async Task에 Virtual Thread 사용
    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    fun asyncTaskExecutor(): AsyncTaskExecutor {
        val executorAdapter = TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
        executorAdapter.setTaskDecorator(LoggingTaskDecorator())
        return executorAdapter
    }



    class LoggingTaskDecorator : TaskDecorator {

        override fun decorate(task: Runnable): Runnable {

            val callerThreadContext = MDC.getCopyOfContextMap()
            return Runnable {
                callerThreadContext?.let {
                    MDC.setContextMap(it)
                }
                task.run()
            }
        }
    }
}