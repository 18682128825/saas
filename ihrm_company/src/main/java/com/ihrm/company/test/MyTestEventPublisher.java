package com.ihrm.company.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MyTestEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    /**
     * 事件发布方法
     */
    public void pushListener(String msg){
        applicationEventPublisher.publishEvent(new MyTestEvent(this,msg));
    }
}
