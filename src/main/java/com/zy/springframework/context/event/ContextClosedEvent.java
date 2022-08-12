package com.zy.springframework.context.event;
/**
 * 关闭
 * */
public class ContextClosedEvent extends ApplicationContextEvent{

    public ContextClosedEvent(Object source) {
        super(source);
    }


}
