package com.stella.free.config

import com.stella.free.util.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@Aspect
@Component
class LoggerAspect(

) {

    private val log = logger()

    // this aspect cause oauth2


    @Pointcut("execution(* com.stella.free.*..*Controller.*(..))")
    private fun controllerCut() = Unit


    @Pointcut("execution(* com.stella.free.service.*Service.*(..))")
    private fun serviceCut() = Unit

    @Before("serviceCut()")
    fun serviceLoggerAdvice(joinPoint: JoinPoint) {
        val type = joinPoint.signature.declaringTypeName
        val method = joinPoint.signature.name

        log.info(
            """
                 service
                 type : $type                
                 method : $method
                 
                 """
        )
    }


    @Before("controllerCut()")
    fun requestLoggerAdvice(joinPoint: JoinPoint) {

        val type = joinPoint.signature.declaringTypeName
        val method = joinPoint.signature.name
        val args = joinPoint.args

        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        log.info (
            """
                               
                 requset url : ${request.servletPath}
                 httpMethod: ${request.method}
                 type : $type
                 methodName : $method
                 """
        )

        if (args.size <= 0) log.info("no parameter")
        for (arg in args) {
            //log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg)
        }
    }





    @AfterThrowing(pointcut = "controllerCut()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Throwable) {
        log.error("An exception has been thrown in " + joinPoint.signature.name + " ${exception.cause}")
    }


}