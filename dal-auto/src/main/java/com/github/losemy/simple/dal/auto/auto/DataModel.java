package com.github.losemy.simple.dal.auto.auto;

import lombok.Data;

/**
 * Description: 从数据来源读取数据模型
 * Created by lose on 19-4-5 下午3:13
 */
@Data
public class DataModel {

    private String name;
    private String type;
    private String comment;
    private String allowNull;
    /**
     * 逗号隔开
     */
    private String indexColumns;
}
