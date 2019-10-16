package com.github.losemy.simple.biz.statemachine;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lose
 * @date 2019-10-13
 **/
@Slf4j
@Service("orderService")
public class OrderServiceImpl {

//    @Autowired
//    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    public static final String stateMachineId = "orderStateMachineId";

    @Autowired
    private StateMachineFactory<OrderStatus, OrderStatusChangeEvent> orderStateMachineFactory;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

    private int id = 1;
    private Map<Integer, Order> orders = new HashMap<>();

    public synchronized Order creat() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_PAYMENT);
        order.setId(id++);
        order.setName("" + order.getId());
        orders.put(order.getId(), order);
        return order;
    }

    public Order pay(int id) {
        Order order = orders.get(id);
        log.info(" 等待支付 -> 等待发货 id=" + id + " threadName=" + Thread.currentThread().getName());
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED).setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            log.info(" 等待支付 -> 等待发货 失败, 状态异常 id=" + id + " threadName=" + Thread.currentThread().getName());
        } else {
            log.info(" 等待支付 -> 等待发货 成功 id=" + id + " threadName=" + Thread.currentThread().getName());
        }
        return orders.get(id);
    }


    public Order deliver(int id) {
        Order order = orders.get(id);
        log.info(" 等待发货 -> 等待收货 id=" + id + " threadName=" + Thread.currentThread().getName());
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY).setHeader("order", order).build(), orders.get(id))) {
            log.info(" 等待发货 -> 等待收货 失败，状态异常 id=" + id + " threadName=" + Thread.currentThread().getName());
        } else {
            log.info(" 等待发货 -> 等待收货 成功 id=" + id + " threadName=" + Thread.currentThread().getName());
        }
        return orders.get(id);
    }


    public Order receive(int id) {
        Order order = orders.get(id);
        log.info(" 等待收货 -> 完成 收货 id=" + id + " threadName=" + Thread.currentThread().getName());
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED).setHeader("order", order).build(), orders.get(id))) {
            log.info(" 等待收货 -> 完成 失败，状态异常 id=" + id + " threadName=" + Thread.currentThread().getName());
        } else {
            log.info(" 等待收货 -> 完成 成功 id=" + id + " threadName=" + Thread.currentThread().getName());
        }
        return orders.get(id);
    }


    public Map<Integer, Order> getOrders() {
        return orders;
    }


    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private boolean sendEvent(Message<OrderStatusChangeEvent> message, Order order) {
        // 按照订单ID的维度锁，分布式下使用分布式锁 或者确保路由到同一台 考虑分布式
        synchronized (String.valueOf(order.getId()).intern()) {
            boolean result = false;
            StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine = orderStateMachineFactory.getStateMachine(stateMachineId);
            log.info("id=" + order.getId() + " 状态机 orderStateMachine" + orderStateMachine);
            try {
                orderStateMachine.start();
                //尝试恢复状态机状态 恢复持久化state
                persister.restore(orderStateMachine, order);
                //same as
                log.info("id=" + order.getId() + " 状态机 orderStateMachine id=" + orderStateMachine.getId());
                result = orderStateMachine.sendEvent(message);
                //持久化状态机状态
                persister.persist(orderStateMachine, order);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                orderStateMachine.stop();
            }
            return result;
        }
    }
}
