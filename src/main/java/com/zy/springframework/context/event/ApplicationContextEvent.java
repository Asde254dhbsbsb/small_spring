package com.zy.springframework.context.event;

import com.zy.springframework.context.ApplicationContext;
import com.zy.springframework.context.ApplicationEvent;
/**
 * 定义事件的抽象类
 * 所有事件都要继承这个类
 * */
public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取 产生这个事件的 ApplicationContext
     * */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
