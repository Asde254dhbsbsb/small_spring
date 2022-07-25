package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.core.io.Resource;
import com.zy.springframework.core.io.ResourceLoader;

/**
 * @author zy
 * @since 2022/7/23  22:49
 */
/**
 * 简单接口--bean定义读取
 * */
public interface BeanDefinitionReader {

    /**
     * 这两个方法都是提供给后面三个方法的工具，加载和注册，
     * 包装在抽象类里避免污染具体接口方法的实现
     * */
    BeanDefinitionRegistry getRegistry() ;

    ResourceLoader getResourceLoader() ;

    void loadBeanDefinition(Resource resource) throws BeansException;

    void loadBeanDefinition(Resource... resources) throws BeansException;

    void loadBeanDefinition(String location) throws BeansException;
}
