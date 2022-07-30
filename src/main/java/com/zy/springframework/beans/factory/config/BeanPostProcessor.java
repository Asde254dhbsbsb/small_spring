package com.zy.springframework.beans.factory.config;

import com.zy.springframework.beans.BeansException;

/**
 * @author zy
 * @since 2022/7/27  11:29
 */

/**
 * 扩展！
 * */
public interface BeanPostProcessor {

    /**
     * 在Bean 对象执行初始化之前，执行此方法
     * */
    Object postProcessBeforeInitialization(Object bean, String beanName ) throws BeansException;

    /**
     * 在Bean 对象执行初始化之后，执行此方法
     * */
    Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException;
}
