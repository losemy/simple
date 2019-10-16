package com.github.losemy.simple.service.exception;

import com.github.losemy.simple.common.constant.CodeEnum;
import com.github.losemy.simple.common.exception.CommonException;

/**
 * @author lose
 * @date 2019-09-25
 **/
public class ServiceException extends CommonException {

    private static final long serialVersionUID = -8952370342510786593L;

    public ServiceException(CodeEnum codeEnum, String... msg) {
        super(codeEnum, msg);
    }

}
