package com.github.losemy.simple.biz.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.github.losemy.simple.biz.mq.domain.AddUserEvent;
import com.github.losemy.simple.integration.es.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lose
 * @date 2019-09-07
 **/
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.userAddTopic}",
        consumerGroup = "user-add-consumer", selectorExpression = "add")
@Slf4j
public class UserAddConsumer implements RocketMQListener<AddUserEvent>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private UserRepository userRepository;

    /**
     * 对象可以自定义 会按照beanCopy的方式copy
     * //直接使用consumer会更好定制
     * @param message
     */
    @Override
    public void onMessage(AddUserEvent message) {
        log.info("user {}", JSON.toJSONString(message));
//        userRepository.save(BeanMapper.map(message, UserES.class));
    }


    /**
     * 有被限速但是限速规则 是针对的线程
     * 也就是实际数量会 * 当前线程数？（为什么不是固定的20）
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        //设置拉取时间间隔
        //1000ms 消费1 个消息 需要可配置 + 线程数 从offset开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setPullBatchSize(5);
        consumer.setPullInterval(2000);
    }



}
