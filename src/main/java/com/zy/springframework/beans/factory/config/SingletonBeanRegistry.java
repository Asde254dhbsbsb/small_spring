package com.zy.springframework.beans.factory.config;

/**
 * @author zy
 * @since 2022/7/23  17:00
 */
/**
 * 定义一个获取单例对象的接口 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
