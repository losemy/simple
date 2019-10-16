package com.github.losemy.simple.common.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author lose
 * @date 2019-10-16
 **/
@Component
@Slf4j
public class EventSubscribeBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private EventBus eventBus;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        EventBusListener eventBusListener = bean.getClass().getAnnotation(EventBusListener.class);
        if(eventBusListener != null){
            log.info("event bus register");
            eventBus.register(bean);
        }
        return bean;

    }


}
