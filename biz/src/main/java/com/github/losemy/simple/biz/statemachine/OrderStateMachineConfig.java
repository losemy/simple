package com.github.losemy.simple.biz.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lose
 * @date 2019-10-13
 **/

@Configuration
@Slf4j
@EnableStateMachineFactory(name = "orderStateMachineFactory")
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

    /**订单状态机ID*/
    public static final String orderStateMachineId = "orderStateMachineId";

    /**
     * 配置状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_DELIVER).event(OrderStatusChangeEvent.PAYED)
                .and()
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE).event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.RECEIVED);
    }

    /**
     * 本地持久化，分布式可以考虑redis
     * 只需要保存状态？
     */
    private Map<Integer,OrderStatus> stateMachines = new ConcurrentHashMap<>();

    @Bean
    public StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatus, OrderStatusChangeEvent, Order>() {
            @Override
            public void write(StateMachineContext<OrderStatus, OrderStatusChangeEvent> context, Order order) throws Exception {
                //本地持久化 分布式考虑redis持久化
                order.setStatus(context.getState());
                log.info("context {}",context.getId());
                stateMachines.put(order.getId(),context.getState());
            }

            @Override
            public StateMachineContext<OrderStatus, OrderStatusChangeEvent> read(Order order) throws Exception {
                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                // 直接通过orderId 获取context数据
                log.info("stateMachines size " + stateMachines.size());
                //没有复原 machineId
                return stateMachines.containsKey(order.getId()) ?
                        new DefaultStateMachineContext<OrderStatus, OrderStatusChangeEvent>(stateMachines.get(order.getId()), null, null, null, null, orderStateMachineId) :
                        new DefaultStateMachineContext<OrderStatus, OrderStatusChangeEvent>(OrderStatus.WAIT_PAYMENT, null, null, null, null, orderStateMachineId);
            }
        });
    }
}
