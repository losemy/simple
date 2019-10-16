package com.github.losemy.simple.common.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author lose
 * @date 2019-10-16
 **/
@EventBusListener
public class StringListener {
    @Subscribe
    @AllowConcurrentEvents
    public void listener(String event) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println("receive msg 2 :"+event);
    }
}
