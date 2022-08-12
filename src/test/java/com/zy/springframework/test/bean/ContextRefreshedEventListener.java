package com.zy.springframework.test.bean;

import com.zy.springframework.context.ApplicationListener;
import com.zy.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("refresh listener");
    }
}
