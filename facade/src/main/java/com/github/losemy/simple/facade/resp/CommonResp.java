package com.github.losemy.simple.facade.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lose
 * @date 2019-09-06
 **/
@Data
public class CommonResp<T> implements Serializable {

    private static final long serialVersionUID = -7390654879497250772L;

    /**
     * 返回码
     */
    private String respCode;
    /**
     * 返回信息
     */
    private String respMsg;

    /**
     * 返回对象
     */
    private T data;

    public CommonResp() {
        this.respCode = "000000";
        this.respMsg = "成功";
    }

    public CommonResp(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }


    public static CommonResp ofError(String respCode, String respMsg) {
        return new CommonResp(respCode, respMsg);
    }
}
