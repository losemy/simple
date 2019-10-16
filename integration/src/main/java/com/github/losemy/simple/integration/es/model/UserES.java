package com.github.losemy.simple.integration.es.model;

import com.github.losemy.simple.integration.es.EsConsts;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author lose
 * @date 2019-09-09
 **/
@Data
@Document(indexName = EsConsts.INDEX_NAME, type = EsConsts.TYPE_NAME, shards = 1, replicas = 0)
public class UserES {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 名字 不进行分词
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 只存放查询数据 大数据放 hbase会比较好
     */
    @Field(type = FieldType.Keyword)
    private String email;

}
