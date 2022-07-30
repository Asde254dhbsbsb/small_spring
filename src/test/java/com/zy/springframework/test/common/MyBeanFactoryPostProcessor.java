package com.zy.springframework.test.common;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.PropertyValue;
import com.zy.springframework.beans.PropertyValues;
import com.zy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author zy
 * @since 2022/7/27  13:55
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为字节跳动"));
    }
}
