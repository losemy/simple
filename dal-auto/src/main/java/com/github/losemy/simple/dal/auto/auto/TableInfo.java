package com.github.losemy.simple.dal.auto.auto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableInfo implements Serializable {

    /**
     *  表名称
     */
    private String name;

    /**
     *  表描述
     */
    private String comment;

    private List<ColumnInfo> columnInfos;

    private List<IndexInfo> indexInfos;


}
