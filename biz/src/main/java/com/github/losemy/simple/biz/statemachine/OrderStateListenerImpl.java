package com.github.losemy.simple.biz.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * @author lose
 * @date 2019-10-13
 **/
@Component("orderStateListener")
@Slf4j
@WithStateMachine(id = OrderStateMachineConfig.orderStateMachineId)
public class OrderStateListenerImpl {

    /**
     * 支付成功之后需要额外通知
     * @param message
     * @return
     */
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        log.info("----------------------------");
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_DELIVER);
        log.info("支付 headers=" + message.getHeaders().toString() + " event=" + message.getPayload());
        log.info("----------------------------");
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        log.info("----------------------------");
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_RECEIVE);
        log.info("发货 headers=" + message.getHeaders().toString() + " event=" + message.getPayload());
        log.info("----------------------------");
        return true;
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message) {
        log.info("----------------------------");
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.FINISH);
        log.info("收货 headers=" + message.getHeaders().toString() + " event=" + message.getPayload());
        log.info("----------------------------");
        return true;
    }
}
