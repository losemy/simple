package com.github.losemy.simple.biz.handler;

import com.github.losemy.simple.common.constant.CodeEnum;
import com.github.losemy.simple.facade.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author lose
 * @date 2019-09-06
 **/
@Slf4j
public class ExceptionHandler {

    public static CommonResp handlerException(Exception e){
        //如何更加优雅的判断异常？
        //本地受检异常 ：自定义异常
        //系统其它模块返回的异常

        log.error("业务层异常",e);
        if(e instanceof DuplicateKeyException){
            return buildCommonResp(CodeEnum.UNKNOWN_ERROR,e.getMessage());
        }

        if(e instanceof SQLIntegrityConstraintViolationException) {

        }

        if(e instanceof Exception){
            return buildCommonResp(CodeEnum.UNKNOWN_ERROR,e.getMessage());
        }

        return buildCommonResp(CodeEnum.UNKNOWN_ERROR);
    }

    /**
     * enum创建返回数据
     * @param codeEnum
     * @param supple 补充信息
     * @return
     */
    private static CommonResp buildCommonResp(CodeEnum codeEnum,String... supple){
        CommonResp commonResp = new CommonResp();
        commonResp.setRespCode(codeEnum.getCode());
        commonResp.setRespMsg(codeEnum.getMsg(supple));
        return commonResp;
    }
}
