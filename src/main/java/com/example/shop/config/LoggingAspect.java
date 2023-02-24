package com.example.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.shop.service.user.*ServiceImpl.*(..))")
    public void services() {
    }

    @Pointcut("execution(* com.example.shop.service.*ServiceImpl.create(..))")
    public void serviceCreate() {
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repositories() {
    }

    @Before("serviceCreate()")
    public void beforeServiceCreate(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        boolean result = Arrays.stream(arguments)
                .anyMatch(Objects::isNull);
        if(result) {
            log.error("Found empty argument during method call '{}': {}, validation failed'",methodName, arguments);
            throw new RuntimeException("Validation failed!");
        }
        log.debug("Validation passed for method call '{}': {}", methodName, arguments);
    }

    @Around("services()")
    public Object aroundServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] arguments = proceedingJoinPoint.getArgs();
        log.debug("Called service method '{}' with arguments: '{}'", methodName, arguments);

        try {
            result = proceedingJoinPoint.proceed();
            log.debug("Service method '{}' returned value '{}'", methodName, result);
        } catch (Throwable e) {
            log.error("Error  {} during service method '{}' call", e.getMessage(), methodName);
            throw e;
        }
        return result;
    }

    @Before(value = "repositories()")
    public void beforeRepositories(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        log.debug("Called repository method '{}' with arguments: '{}'", methodName, arguments);
    }

    @After(value = "repositories()")
    public void afterRepositories(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.debug("Repository method '{}' execute successfully ", methodName);
    }
}
