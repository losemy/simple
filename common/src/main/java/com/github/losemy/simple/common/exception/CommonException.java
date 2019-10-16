package com.github.losemy.simple.common.exception;

import cn.hutool.core.collection.CollUtil;
import com.github.losemy.simple.common.constant.CodeEnum;
import lombok.Data;

/**
 * @author lose
 * @date 2019-09-25
 **/
@Data
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 2404379882804268464L;

    private String code;
    private String message;

    public CommonException(CodeEnum codeEnum,String... msg){
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMsg(msg);
    }
}
