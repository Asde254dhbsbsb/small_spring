package com.zy.springframework.beans.factory;


import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author zy
 * @since 2022/7/23  22:51
 */

/**
 * 提供分析和修改Bean以及预先实例化的操作接口
 * */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
