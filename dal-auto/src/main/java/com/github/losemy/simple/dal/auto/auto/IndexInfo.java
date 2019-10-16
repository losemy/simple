package com.github.losemy.simple.dal.auto.auto;

import lombok.Data;

/**
 * Description: utils
 * Created by lose on 19-4-5 下午2:36
 */
@Data
public class IndexInfo {

    /**
     * 索引名称
     */
    private String name;

    /**
     * 索引列
     */
    private String indexColumns;

    /**
     * 类型
     * index
     * unique
     * fulltext
     */
    private String type;

}
