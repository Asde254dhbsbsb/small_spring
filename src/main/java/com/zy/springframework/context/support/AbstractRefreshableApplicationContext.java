package com.zy.springframework.context.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.zy.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author zy
 * @since 2022/7/27  11:32
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    /**
     * 获取BeanFactory实例化以及对资源配置的加载操作
     *
     * */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinition(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
    /**
     * 加载完成后，即可完成对xml配置文件中Bean对象的定义和注册，
     * 同时也包括实现了接口BeanFactoryPostProcessor、BeanPostProcessor的配置Bean信息
     * 抽象方法 继续由其他抽象继承类实现
     */

    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory) throws BeansException;
}
