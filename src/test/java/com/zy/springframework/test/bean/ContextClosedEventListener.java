package com.zy.springframework.test.bean;

import com.zy.springframework.context.ApplicationListener;
import com.zy.springframework.context.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("close listener");
    }
}
