package com.github.losemy.simple.facade.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lose
 * @date 2019-09-06
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8581625898558814850L;


    private Long id;


    @NotEmpty(message="昵称name不能为空", groups={Add.class})
    private String name;

    /**
     * 密码（加密方式MD5）
     */
    @NotEmpty(message = "密码password不能为空", groups={Add.class})
    private String password;

    /**
     * 邮箱
     */
    private String email;

    public interface Add{}
}
