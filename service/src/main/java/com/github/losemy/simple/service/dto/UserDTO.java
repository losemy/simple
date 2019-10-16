package com.github.losemy.simple.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Description: simple
 * Created by lose on 2019/8/27 9:18
 * @author lose
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = UserDTO.Update.class, message="名称不能为空")
    private Long id;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 用户名
     */
    @NotEmpty(groups = UserDTO.Add.class, message="名称不能为空")
    private String name;

    /**
     * 密码（加密方式MD5）
     */
    @NotEmpty(groups = UserDTO.Add.class, message="密码不能为空")
    private String password;

    /**
     * 邮箱
     */
    private String email;


    public interface Add{}

    public interface Update{}

}
