package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.BeanFactory;
import com.zy.springframework.beans.factory.config.BeanDefinition;

/**
 * @author zy
 * @since 2022/7/23  17:02
 */
/**
 * 继承了单例注册类，那么就有了单例Bean的注册功能
 * 对BeanFactory的实现，
 * 在getBean方法中，获取不到时需要拿到Bean定义做相应的实例化
 * 并没有自身实现这些方法，而是定义了调用过程以及提供了抽象方法  由实现此抽象类的其他类做相应调用
 * */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name , args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    public <T> T doGetBean(final String name, final Object... args) throws BeansException {
        Object bean = getSingleton(name);
        if(bean != null) {
            return (T) bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException ;
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException ;
}
