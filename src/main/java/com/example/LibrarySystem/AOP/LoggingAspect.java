package com.example.LibrarySystem.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.LibrarySystem.Controller.*..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Method execution started: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.example.LibrarySystem.Controller.*..*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method execution finished: " + joinPoint.getSignature());
        System.out.println("\n=====>>> result is: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.LibrarySystem.Controller.*..*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: " + joinPoint.getSignature());
        logger.error("Exception message: " + exception.getMessage());
    }

    @Around("execution(* com.example.LibrarySystem.Service.BookService.BookService.addBook(..)) || " +
            "execution(* com.example.LibrarySystem.Service.BookService.BookService.updateBook(..)) || " +
            "execution(* com.example.LibrarySystem.Service.PatronService.PatronService.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        logger.info(joinPoint.getSignature().toShortString() + " executed in " + executionTime + "ms");
        return result;
    }
}
