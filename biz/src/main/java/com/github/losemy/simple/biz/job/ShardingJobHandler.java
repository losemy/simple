package com.github.losemy.simple.biz.job;

import com.github.losemy.simple.biz.job.sharding.ShardingContext;
import com.github.losemy.simple.biz.job.sharding.ShardingStrategy;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lose
 * @date 2019-09-10
 **/
@JobHandler(value="shardingJobHandler")
@Service
@Slf4j
public class ShardingJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        log.info("param {}",param);
        // 分片参数

        try {
            ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
            XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());
            ShardingContext context = new ShardingContext();
            context.setShardingItem(shardingVO.getIndex());
            context.setShardingParam(param);
            context.setShardingTotalCount(shardingVO.getTotal());

            //直接new不太好？
            List<String> jobs = ShardingStrategy.getShardingInfo(context);
            XxlJobLogger.log("第 {} 片, 命中分片开始处理任务 {}", context.getShardingItem(),jobs);

            for(String job: jobs){
                //这里job其实就是参数 参数属于对等服务
                //do 具体job
            }
            //广播任务 分片任务
            // 命中处理 根据 分片数量决定 如果 total > 1
            // 参数 param 转换成list list.size() > total 的分片策略 需要扩展
            // 业务逻辑

        }catch(Exception e){
            log.error("任务处理失败",e);
            return FAIL;
        }

        return SUCCESS;
    }

}
