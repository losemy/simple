package com.github.losemy.simple.common.eventbus;

import com.github.losemy.simple.common.thread.LogRejectedHandler;
import com.github.losemy.simple.common.thread.MonitorThreadPoolExecutor;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author lose
 * @date 2019-10-16
 **/
@Configuration
@ConfigurationProperties(prefix = "eventbus.config")
@Data
public class SimpleEventBus {


    private String name = "event";

    @Bean
    public EventBus eventBus(){

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("eventBus-pool-%d").build();

        // 自定义拒绝策略拦截 实现对应接口即可 需要保持thread不变才好定制
        // 需要监控线程池的运行避免出现问题 运行慢
        ExecutorService pool = new MonitorThreadPoolExecutor(5, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1024), namedThreadFactory, new LogRejectedHandler(),"eventBus");

        return new AsyncEventBus(name, pool);
    }


}
