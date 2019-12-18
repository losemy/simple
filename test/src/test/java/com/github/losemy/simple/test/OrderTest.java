package com.github.losemy.simple.test;

import com.github.losemy.simple.biz.statemachine.OrderServiceImpl;
import com.github.losemy.simple.run.SimpleApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lose
 * @date 2019-10-13
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleApplication.class)
public class OrderTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void testMultThread() throws InterruptedException {
        orderService.creat();
        orderService.creat();
        orderService.creat();

        orderService.pay(1);

        orderService.pay(3);

        new Thread(()->{
            orderService.deliver(1);
            orderService.receive(1);
            orderService.deliver(3);
            orderService.receive(3);
        }).start();

        orderService.receive(1);

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        log.info("service {}",orderService.getOrders());
    }
}
