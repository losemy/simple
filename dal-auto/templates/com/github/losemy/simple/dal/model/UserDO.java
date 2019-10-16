package com.github.losemy.dal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.losemy.dal.model.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accesimplers;

/**
 * <p>
 * 
 * </p>
 *
 * @author lose
 * @since 2019-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accesimplers(chain = true)
@TableName("user")
public class UserDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("NAME")
    private String name;

    /**
     * 密码（加密方式MD5）
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;


    public static final String NAME = "NAME";

    public static final String PASSWORD = "PASSWORD";

    public static final String EMAIL = "EMAIL";

}
