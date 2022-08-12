package com.zy.springframework.beans.factory;
/**
 * 定义FactoryBean接口
 * */
public interface FactoryBean<T>{
    T getObject(); // 获取FactoryBean里的对象bean
    Class<?> getObjectType();
    boolean isSingleton();
}
