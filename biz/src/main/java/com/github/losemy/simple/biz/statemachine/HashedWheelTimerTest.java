package com.github.losemy.simple.biz.statemachine;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lose
 * @date 2019-10-14
 **/
public class HashedWheelTimerTest {

    public static void main(String[] argv) {

        final Timer timer = new HashedWheelTimer(Executors.defaultThreadFactory(), 5, TimeUnit.SECONDS, 2);

        // 更加实用的时间轮 适用大量任务堆叠
        TimerTask task1 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("task 1 will run per 5 seconds ");
                timer.newTimeout(this, 5, TimeUnit.SECONDS);//结束时候再次注册
            }
        };
        timer.newTimeout(task1, 5, TimeUnit.SECONDS);



        TimerTask task2 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("task 2 will run per 5 seconds");
                timer.newTimeout(this, 5, TimeUnit.SECONDS);//结束时候再注册
            }
        };
        timer.newTimeout(task2, 5, TimeUnit.SECONDS);


        TimerTask task3 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("task 3 will run per 5 seconds");
                timer.newTimeout(this, 5, TimeUnit.SECONDS);//结束时候再注册
            }
        };
        timer.newTimeout(task3, 5, TimeUnit.SECONDS);


        //该任务仅仅运行一次
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("task 4 run only once ! ");
            }
        }, 15, TimeUnit.SECONDS);

    }

}
