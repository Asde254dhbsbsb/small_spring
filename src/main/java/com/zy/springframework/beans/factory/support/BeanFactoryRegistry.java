package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.factory.config.BeanDefinition;

/**
 * @author zy
 * @since 2022/7/23  17:05
 */
public interface BeanFactoryRegistry {
    public void registryBeanFactory(String beanName, BeanDefinition beanDefinition) ;
}
