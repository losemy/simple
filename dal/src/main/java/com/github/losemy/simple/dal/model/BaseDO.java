package com.github.losemy.simple.dal.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: simple
 * Created by lose on 2019/8/26 17:17
 * @author lose
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    /**Collectors
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
