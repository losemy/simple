package com.github.losemy.simple.web.vo;

import com.github.losemy.simple.common.constant.CodeEnum;
import com.github.losemy.simple.common.exception.CommonException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description: simple
 * Created by lose on 2019/8/27 14:12
 * @author lose
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResultVO<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String respCode;
    /**
     * 返回信息
     */
    private String respMsg;

    /**
     * 数据
     */
    private T data;

    public ResultVO(){
        this.respCode="000000";
        this.respMsg="成功";
    }

    public ResultVO(String respCode,String respMsg,T data){
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.data = data;
    }

    public static <T extends Serializable> ResultVO<T> of(String code, String message, T data) {
        return new ResultVO<T>(code, message, data);
    }

    public static <T extends Serializable> ResultVO<T> ofSuccess(T data) {
        return of("000000","成功", data);
    }

    public static <T extends Serializable> ResultVO<T> ofMessage(String message,T data) {
        return of("000000",message, data);
    }

    public static <T extends Serializable> ResultVO<T> ofCode(CodeEnum codeEnum, T data) {
        return of(codeEnum.getCode(), codeEnum.getMsg(), data);
    }

    public static <E extends CommonException,T extends Serializable> ResultVO<T> ofException(E e,T data) {
        return of(e.getCode(), e.getMessage(),null);
    }

}
