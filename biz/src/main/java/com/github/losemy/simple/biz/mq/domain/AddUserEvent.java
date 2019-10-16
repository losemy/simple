package com.github.losemy.simple.biz.mq.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lose
 * @date 2019-09-07
 * 需要消费服务方biz 方便消费其他服务
 **/
@Data
public class AddUserEvent implements Serializable {
    private Long id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码（加密方式MD5）
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

}
