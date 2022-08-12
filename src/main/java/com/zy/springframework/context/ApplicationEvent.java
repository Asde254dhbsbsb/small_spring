package com.zy.springframework.context;

import java.util.EventObject;
/**
 * 以继承EventObject
 * 定义出具备事件功能的抽象类ApplicationEvent
 * 后续所以事件的类都要继承这个类
 * */
public abstract class ApplicationEvent extends EventObject {
    /**
     * 构造一个 prototypical 事件
     * */
    public ApplicationEvent(Object source) {
        super(source);
    }

}
