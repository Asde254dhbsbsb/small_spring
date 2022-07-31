package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现FactoryBean的注册服务
 * */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{
    /**
     * 缓存由FactoryBeans创建的单例对象
     * */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCacheObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {

            }
        }
    }

}
