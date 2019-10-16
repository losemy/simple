package com.github.losemy.simple.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Description: simple
 *
 * @author lose on 2019/9/4 15:03
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 只支持昵称登录
     */
    @NotEmpty(message = "昵称为空")
    private String name;
    @NotEmpty(message="密码不能为空")
    private String password;
    @NotEmpty(message = "回调地址不能为空")
    private String backUrl;

}
