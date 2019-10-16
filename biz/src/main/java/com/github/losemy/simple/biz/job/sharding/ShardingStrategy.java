package com.github.losemy.simple.biz.job.sharding;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lose
 * @date 2019-09-10
 * 分片策略 算法参考elastic JobShardingStrategy实现
 **/
public class ShardingStrategy{

    /**
     * 平均算法 多余的分配到最后一个
     * @param shardingContext
     * @return
     */
    public static List<String> getShardingInfo(ShardingContext shardingContext) {

        //解析
        String shardingParam = shardingContext.getShardingParam();

        int shardingTotalCount = shardingContext.getShardingTotalCount();

        int shardingItem = shardingContext.getShardingItem();

        List<String> shardingParams = StrUtil.split(shardingParam,",",0,true,true);


        int paramSize = shardingParams.size();

        //分配方案 保证有序 即在每个系统上算出来都是一样的

        int itemCountPerSharding = paramSize / shardingTotalCount;
        int count = 0;
        // 生成 map
        Map<Integer,List<String>> shardingMap = new HashMap<>();

        //最后一个需要能够将多余的消化掉
        for (int jobI=0; jobI < shardingTotalCount; jobI++) {
            List<String> shardingItems = new ArrayList<>(itemCountPerSharding + 1);

            if(count != (shardingTotalCount-1)) {
                for (int i = count * itemCountPerSharding; i < (count + 1) * itemCountPerSharding; i++) {
                    shardingItems.add(shardingParams.get(i));
                }
            }else{
                //需要加入所有分片数据
                for (int i = count * itemCountPerSharding; i < paramSize; i++) {
                    shardingItems.add(shardingParams.get(i));
                }
            }
            shardingMap.put(jobI, shardingItems);
            count++;
        }

        return shardingMap.get(shardingItem);

    }

}
