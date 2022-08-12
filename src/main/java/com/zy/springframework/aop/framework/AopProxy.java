package com.zy.springframework.aop.framework;
/**
 * 定义一个标准接口，用于获取代理类。因为具体实现代理的方式可以有JDK方式，
 * 也可以是Cglib方式，所以定义接口更加方便管理实现类
 * */
public interface AopProxy {
    Object getProxy();
}
