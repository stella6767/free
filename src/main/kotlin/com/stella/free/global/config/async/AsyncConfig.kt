package com.stella.free.global.config.async


import com.stella.free.global.util.logger
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.lang.reflect.Method
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor


@EnableScheduling
@EnableAsync
@Configuration
class AsyncConfig(

) : AsyncConfigurer {

    /**
     *  최초 coreSize개의 스레드에서 처리하다가 처리 속도가 밀릴 경우
     *  queueCapacity 사이즈 queue에서 대기하고 그보다 많은 요청이 들어올 경우
     *  최대 maxSize 스레드까지 생성해서 처리하게 된다.
     *  https://jeonyoungho.github.io/posts/ThreadPoolTaskExecutor/
     */

    val coreSize = 10
    val maxSize = 50
    val queueCapacity = 10
    val threadPrefix = "free-"
    val scheduledPrefix = "free-scheduled"

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = coreSize
        executor.maxPoolSize = maxSize
        executor.queueCapacity = queueCapacity
        executor.setThreadNamePrefix(threadPrefix)
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())

        executor.initialize()
        return executor
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return CustomAsyncExceptionHandler()
    }

    @Bean
    fun schedulerTasks(): TaskScheduler {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = coreSize
        threadPoolTaskScheduler.setThreadNamePrefix(scheduledPrefix)
        threadPoolTaskScheduler.initialize()
        return threadPoolTaskScheduler
    }


    class CustomAsyncExceptionHandler : AsyncUncaughtExceptionHandler {
        val log = logger()
        override fun handleUncaughtException(ex: Throwable, method: Method, vararg params: Any?) {
            log.error ("""                
                Exception message: ${ex.message}
                Method Name: ${method.name}                                            
            """.trimIndent() )
        }

    }

}