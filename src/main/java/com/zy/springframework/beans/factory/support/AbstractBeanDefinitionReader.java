package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.core.io.DefaultResourceLoader;
import com.zy.springframework.core.io.ResourceLoader;


/**
 * @author zy
 * @since 2022/7/23  22:49
 */

/**
 * 用抽象类实现获取Bean定义注册和资源加载器
 * */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this(registry, new DefaultResourceLoader());
    }

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }
    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
