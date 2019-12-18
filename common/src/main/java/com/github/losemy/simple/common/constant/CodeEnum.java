package com.github.losemy.simple.common.constant;

import cn.hutool.core.util.StrUtil;

/**
 * @author lose
 * @date 2019-09-06
 * 内容支持%s格式
 **/
public enum CodeEnum {
    SUCCESS(CodeConstant.SUCCESS,"成功:%s"),
    PARAM_VALID_ERROR(CodeConstant.PARAM_VALID_ERROR,"参数校验失败:%s"),
    DATA_DUPLICATION(CodeConstant.DATA_DUPLICATION,"数据重复请检查数据:%s"),
    UNKNOWN_ERROR(CodeConstant.UNKNOWN_ERROR,"未知异常:%s"),
    SSO_ERROR(CodeConstant.SSO_ERROR,"登录失败:%s"),
    SERVICE_ERROR(CodeConstant.SERVICE_ERROR, "service异常:%s");

    private String code;
    private String msg;

    CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return StrUtil.replaceIgnoreCase(this.msg,":%s","");
    }

    /**
     * 避免没有传入
     * 需要保证足够安全
     * @param supple
     * @return
     */
    public String getMsg(String... supple){
        if(supple != null && supple.length > 0) {
            return String.format(this.msg, supple);
        }else{
            return getMsg();
        }
    }

}
