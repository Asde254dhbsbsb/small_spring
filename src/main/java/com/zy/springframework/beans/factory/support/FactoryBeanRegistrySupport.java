package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现FactoryBean的注册服务
 * 主要处理的就是关于FactoryBean此类对象的注册操作，
 * 之所以放到这样一个单独的类里，就是希望做到不同领域模块下只负责各自需要完成的功能
 * 避免因为扩展导致类膨胀到难以维护
 * */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{
    /**
     * 缓存由FactoryBeans创建的单例对象，避免重复创建
     * */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCacheObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) throws BeansException {
        if (factory.isSingleton()) { // 是单例的话 放进去
            System.out.println("我是单例的：" + beanName);
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 具体的获取 FactoryBean # getObject()方法
     * 因为既有缓存的处理也有对象的获取，所以额外提供了 getObjectFromFactoryBean进行逻辑包装
     * 如果没有获取到就创建并存入缓存；
     * */
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) throws BeansException {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
