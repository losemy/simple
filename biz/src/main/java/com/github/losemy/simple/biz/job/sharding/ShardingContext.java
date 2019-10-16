package com.github.losemy.simple.biz.job.sharding;

import lombok.Data;

/**
 * @author lose
 * @date 2019-09-10
 **/
@Data
public class ShardingContext {
    /**
     * 分片任务参数
     * 数据格式
     * name,name1
     */
    private String shardingParam;

    private Integer shardingTotalCount;

    private Integer shardingItem;
}
