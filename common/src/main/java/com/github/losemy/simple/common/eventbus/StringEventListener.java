package com.github.losemy.simple.common.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author lose
 * @date 2019-10-16
 **/
@EventBusListener
public class StringEventListener{

    /**
     * 模拟长时间消耗
     * 同一个消息允许被多次消费
     * @param event
     * @throws InterruptedException
     */
    @Subscribe
    @AllowConcurrentEvents
    public void listener(String event) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("receive msg 1 :"+event);
    }
}
