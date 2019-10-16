package com.github.losemy.simple.biz.aop;

import com.github.losemy.simple.biz.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author lose
 * @date 2019-09-06
 **/
@Component
@Slf4j
@Aspect
@Order(100)
public class ExceptionAspect {

    @Pointcut("execution(* com.github.losemy.simple.biz.impl..*.*(..))")
    public void pointcut() { }

    /**
     * 异常处理
     * @param joinPoint
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            return joinPoint.proceed();
        }catch (Exception e){
            //区分异常
            return ExceptionHandler.handlerException(e);
        }
    }

}
