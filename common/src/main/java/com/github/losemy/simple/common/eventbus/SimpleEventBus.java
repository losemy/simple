package com.github.losemy.simple.common.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

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

        //自定义拒绝策略拦截
        ExecutorService pool = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


        return new AsyncEventBus(name, pool);
    }


}
