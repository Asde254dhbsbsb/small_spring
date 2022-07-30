package com.zy.springframework.beans.factory.config;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author zy
 * @since 2022/7/27  11:29
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的BeanDefinition 加载完成后，实例化Bean 对象之前，
     * 提供修改BeanDefinition 属性的机制
     * */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
