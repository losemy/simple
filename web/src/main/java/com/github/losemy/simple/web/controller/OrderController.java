package com.github.losemy.simple.web.controller;

import cn.hutool.core.util.RandomUtil;
import com.github.losemy.simple.biz.statemachine.Order;
import com.github.losemy.simple.biz.statemachine.OrderServiceImpl;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lose
 * @date 2019-10-15
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private EventBus eventBus;


    @RequestMapping("/com.github.losemy.simple.test")
    public Order testOrder(){
        Order order = orderService.creat();
        orderService.pay(order.getId());
        orderService.deliver(order.getId());
        orderService.receive(order.getId());
        log.info("order {}",order);
        return order;
    }

    @RequestMapping("/event")
    public void event(){
        eventBus.post("123" + RandomUtil.randomString("asdasdadasdwlrowo457fa",3));
    }

}
