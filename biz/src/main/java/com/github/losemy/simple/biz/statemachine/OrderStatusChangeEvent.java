package com.github.losemy.simple.biz.statemachine;

/**
 * @author lose
 * @date 2019-10-13
 **/
public enum OrderStatusChangeEvent {
    // 支付，发货，确认收货
    PAYED, DELIVERY, RECEIVED;
}
