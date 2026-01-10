package freeapp.life.stella.api.config



import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.core.task.TaskDecorator
import org.springframework.core.task.support.TaskExecutorAdapter
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import java.lang.reflect.Method
import java.util.concurrent.Executor
import java.util.concurrent.Executors


@EnableScheduling
@EnableAsync
@Configuration
class AsyncConfig(

) : AsyncConfigurer {


    private val log = KotlinLogging.logger {  }
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
        val log = KotlinLogging.logger {  }
        override fun handleUncaughtException(ex: Throwable, method: Method, vararg params: Any?) {

            log.error ("""                
                Exception message: ${ex.message}
                Method Name: ${method.name}                                            
            """.trimIndent() )


        }

    }

}