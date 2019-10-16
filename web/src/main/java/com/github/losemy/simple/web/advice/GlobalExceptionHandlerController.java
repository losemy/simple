package com.github.losemy.simple.web.advice;


import cn.hutool.core.util.StrUtil;
import com.github.losemy.simple.common.constant.CodeEnum;
import com.github.losemy.simple.common.exception.CommonException;
import com.github.losemy.simple.web.vo.ResultVO;
import com.github.losemy.sso.client.exception.SSOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: simple
 * Created by lose on 2019/8/27 9:43
 * @author lose
 * 异常需要细分 处理
 * Exception 用来收尾避免异常抛出
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ResultVO handleBindException(BindException ex){
        log.error("参数校验异常",ex);
        List<String> defaultMsg = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResultVO.ofCode(CodeEnum.PARAM_VALID_ERROR, StrUtil.join(",",defaultMsg));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO handleBindGetException(ConstraintViolationException ex){
        log.error("单个参数校验异常",ex);
        List<String> defaultMsg = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return ResultVO.ofCode(CodeEnum.PARAM_VALID_ERROR, StrUtil.join(",",defaultMsg));
    }

    /**
     * 内部业务处理全部继承这个exception
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(CommonException.class)
    public ResultVO handleCommonException(CommonException ex) throws Exception{
        return ResultVO.ofException(ex,null);
    }


    @ExceptionHandler(Exception.class)
    public ResultVO handleException(HttpServletResponse resp,Exception ex) throws Exception {
        String msg = ex.getMessage();
        return ResultVO.ofCode(CodeEnum.UNKNOWN_ERROR,msg);
    }

    @ExceptionHandler(SSOException.class)
    public ResultVO handleSSOException(HttpServletResponse resp,Exception ex) throws Exception {
        String msg = ex.getMessage();
        return ResultVO.ofCode(CodeEnum.SSO_ERROR,msg);
    }


}
