package com.stella.free.global.config.async


import com.stella.free.global.util.logger
import org.slf4j.MDC
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.core.task.TaskDecorator
import org.springframework.core.task.support.TaskExecutorAdapter
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.lang.reflect.Method
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


@EnableScheduling
@EnableAsync
@Configuration
class AsyncConfig(

) : AsyncConfigurer {

    /**
     *  플랫폼 스레드를 이용한 Async thread pool 설명
     *  최초 coreSize개의 스레드에서 처리하다가 처리 속도가 밀릴 경우
     *  queueCapacity 사이즈 queue에서 대기하고 그보다 많은 요청이 들어올 경우
     *  최대 maxSize 스레드까지 생성해서 처리하게 된다.
     *  https://jeonyoungho.github.io/posts/ThreadPoolTaskExecutor/
     */

    private val log = logger()

    val coreSize = 10
    val maxSize = 50
    val queueCapacity = 10
    val threadPrefix = "free-"
    val scheduledPrefix = "free-scheduled"


//    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
//    fun asyncTaskExecutor(): AsyncTaskExecutor {
//        val executorAdapter = TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
//        executorAdapter.setTaskDecorator(LoggingTaskDecorator())
//
//        return executorAdapter
//    }


    @Bean
    fun taskExecutor(): AsyncTaskExecutor {
        val executorAdapter = TaskExecutorAdapter(asyncExecutor)
        executorAdapter.setTaskDecorator(LoggingTaskDecorator())
        return executorAdapter
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return CustomAsyncExceptionHandler()
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


    override fun getAsyncExecutor(): Executor {
//        val executor = ThreadPoolTaskExecutor()
//        executor.corePoolSize = coreSize
//        executor.maxPoolSize = maxSize
//        executor.queueCapacity = queueCapacity
//        executor.setThreadNamePrefix(threadPrefix)
//        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
//        executor.setWaitForTasksToCompleteOnShutdown(true) // graceful
//        executor.initialize()

        val factory = Thread.ofVirtual().name("virtual-thread", 1)
            .uncaughtExceptionHandler { t, e ->
                log.error(
                    """
                Uncaught exception in thread
                Exception message: ${e.message}
                thread Name: ${t.name}                                            
            """.trimIndent()
                )
            }
            .factory()

        return Executors.newThreadPerTaskExecutor(factory)
    }

//    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
//        return CustomAsyncExceptionHandler()
//    }
//
//
//    /**
//     * TaskScheduler config
//     */
    @Bean
    fun schedulerTasks(): TaskScheduler {
//        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
//        threadPoolTaskScheduler.poolSize = coreSize
//        threadPoolTaskScheduler.setThreadNamePrefix(scheduledPrefix)
//        threadPoolTaskScheduler.initialize()

        return ConcurrentTaskScheduler(
            Executors.newScheduledThreadPool(0, Thread.ofVirtual().factory())
        )
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