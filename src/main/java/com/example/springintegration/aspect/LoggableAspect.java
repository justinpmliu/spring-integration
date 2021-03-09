package com.example.springintegration.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggableAspect {

    @Pointcut("@annotation(Timed)")
    public void executeTiming(){}

    @Around("executeTiming()")
    public Object logMethodTiming(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object returnValue = proceedingJoinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        StringBuilder message = new StringBuilder("Method: ");
        message.append(proceedingJoinPoint.getTarget().getClass().getName());
        message.append(".");
        message.append(proceedingJoinPoint.getSignature().getName());
        message.append(" | ");
        message.append(totalTime / 1000.0).append(" seconds");
        log.info(message.toString());
        return returnValue;
    }


}
