package com.zy.springframework.beans.factory;

import com.zy.springframework.beans.BeansException;

/**
 * @author zy
 * @since 2022/7/23  16:57
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
    Object getBean(String name, Object...args) throws BeansException ;
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
