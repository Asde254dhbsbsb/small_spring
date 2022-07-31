package com.zy.springframework.beans.factory;

import com.zy.springframework.beans.BeansException;

/**
 * 实现此接口，即能感知所属的BeanFactory
 * */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
