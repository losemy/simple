package com.github.losemy.simple.facade.annotation;

import java.lang.annotation.*;

/**
 * @author lose
 * @date 2019-09-06
 **/
@Target( {ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
//无效注解 只在对类的注解上有效
//@Inherited
@Documented
public @interface ParamValid {

    /**
     * 主要用来分组校验
     * @return
     */
    Class<?>[] value() default {};
}
