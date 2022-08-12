package com.zy.springframework.context.event;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.context.ApplicationEvent;
import com.zy.springframework.context.ApplicationListener;

/**
 * 事件广播器
 * */
public interface ApplicationEventMulticaster {

    /**
     * 添加一个监听器
     * */
    void addApplicationListener(ApplicationListener<?> listener) ;

    /**
     * 从列表去除一个监听器
     * */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播给定的应用事件给监听器
     * 最终推送事件消息会经过这个接口方法来处理谁该接收事件
     * */
    void multicastEvent(ApplicationEvent event) throws BeansException;
}
