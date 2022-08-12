package com.zy.springframework.context.event;
/**
 * 监听刷新
 * */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
