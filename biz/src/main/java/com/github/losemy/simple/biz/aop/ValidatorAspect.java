package com.github.losemy.simple.biz.aop;

import com.github.losemy.simple.common.constant.CodeConstant;
import com.github.losemy.simple.common.validate.ValidatorUtil;
import com.github.losemy.simple.facade.annotation.ParamValid;
import com.github.losemy.simple.facade.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author lose
 * @date 2019-09-06
 * order 越小越先加载
 **/
@Component
@Slf4j
@Aspect
@Order(-1)
public class ValidatorAspect {


    @Pointcut("execution(* com.github.losemy.simple.biz.impl..*.*(..))")
    public void pointcut() {}

    /**
     * 入参校验
     * @param joinPoint
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        CommonResp commonResp = new CommonResp();


        Object[] params = joinPoint.getArgs();
        if(params.length == 0){
            return commonResp;
        }
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        // 获取不到接口的注解 只能在实现类上写
        if(method.getReturnType().equals(CommonResp.class)) {

            //参数注解，1维是参数，2维是注解
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                Object param = params[i];
                Annotation[] paramAnn = annotations[i];
                //没有注解，直接下一个参数
                if (paramAnn.length == 0) {
                    continue;
                }
                for (Annotation annotation : paramAnn) {
                    //这里判断当前注解是否为ParamValid.class
                    if (annotation.annotationType().equals(ParamValid.class)) {
                        ParamValid paramValid = (ParamValid) annotation;
                        ValidatorUtil.ValidResult validResult = ValidatorUtil.validate(param, paramValid.value());
                        //校验到异常快速失败
                        if (validResult.isError()) {
                            return CommonResp.ofError(CodeConstant.PARAM_VALID_ERROR, validResult.getMsg());
                        }
                        break;
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
