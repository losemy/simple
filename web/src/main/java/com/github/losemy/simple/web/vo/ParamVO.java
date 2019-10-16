package com.github.losemy.simple.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: simple
 * Created by lose on 2019/8/27 14:15
 * @author lose
 */
@Data
public class ParamVO<T extends Serializable> implements Serializable{
    private static final long serialVersionUID = 1L;

    // 通用字段设计 方便调用传输
    /**
     * 参数
     */
    private T param;

}
