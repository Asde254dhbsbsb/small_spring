package com.zy.springframework.beans.factory.config;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessorBeforeInstantiation(Class<?> beanClass, String beanName);
}
