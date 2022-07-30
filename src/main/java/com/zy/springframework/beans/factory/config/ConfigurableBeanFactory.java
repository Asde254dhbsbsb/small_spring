package com.zy.springframework.beans.factory.config;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author zy
 * @since 2022/7/24  10:10
 */
/**
 * 可获取BeanPostProcessor、BeanClassLoader等的一个配置化接口
 * */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();
}
