package com.local.ducdv.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AspectLoggerService {
    private final Logger logger = LoggerFactory.getLogger(AspectLoggerService.class);

    @Before("execution(* com.local.ducdv.mapper.*.*(..))")
    public void before(JoinPoint joinPoint){
        logger.info("before called " + joinPoint.toString());
    }

    @After("execution(* com.local.ducdv.mapper.*.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info("after called " + joinPoint.toString());
    }

    @AfterReturning("execution(* com.local.ducdv.mapper.*.*(..))")
    public void afterReturning(JoinPoint joinPoint) {
        logger.info("afterReturning called " + joinPoint.toString());
    }

    @AfterThrowing("execution(* com.local.ducdv.mapper.*.*(..))")
    public void afterThrowing(JoinPoint joinPoint) {
        logger.info("afterThrowing called " + joinPoint.toString());
    }

    @Around("execution(* com.local.ducdv.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        logger.info("Start Time Taken by {} is {}", joinPoint, startTime);
        Object around = joinPoint.proceed();

        Long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
        return around;
    }

    @Around("@annotation(com.local.ducdv.config.AspectTrackTime)")
    public Object aroundTrackTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        logger.info("Start Time Taken by {} is {}", joinPoint, startTime);
        Object around = joinPoint.proceed();

        Long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
        return around;
    }
}
