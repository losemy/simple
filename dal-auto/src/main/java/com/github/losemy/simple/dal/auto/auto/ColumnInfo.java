package com.github.losemy.simple.dal.auto.auto;

import lombok.Data;

@Data
public class ColumnInfo {

    /**
     * 列名称
     */
    private String name;

    /**
     * 根据数据库类型设置
     * es：varchar(32)
     */
    private String type;

    /**
     * 列描述
     */
    private String comment;

    /**
     * 是否允许为空
     */
    private boolean allowNull;


}
