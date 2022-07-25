package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zy
 * @since 2022/7/23  17:01
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 单例对象缓存
     * */
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 实现一个受保护的添加单例对象的方法，可以被继承此类的其他类调用， 如AbstractBeanFactory等
     * */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
