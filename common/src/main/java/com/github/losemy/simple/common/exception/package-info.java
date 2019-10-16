package com.github.losemy.simple.common.exception;

/**
 *
 * 主要用来整合区分异常类型
 * 一个项目可以有一个或者多个
 * 在biz层使用aop拦截
 * 在web使用@ControllerAdvice拦截
 *
 * 注意所有层都不显示try catch交由上层处理，除非自身可以处理异常
 */