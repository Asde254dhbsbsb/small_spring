package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import java.lang.reflect.Constructor;
/**
 * Constructor-------->为了拿到相对于的构造函数
 * */
/**
 * @author zy
 * @since 2022/7/23  19:50
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
