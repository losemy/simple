package com.github.losemy.simple.dal.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

}
