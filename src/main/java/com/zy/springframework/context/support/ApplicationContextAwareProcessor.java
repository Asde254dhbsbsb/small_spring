package com.zy.springframework.context.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.context.ApplicationContext;
import com.zy.springframework.context.ApplicationContextAware;

/**
 * 由于ApplicationContext的获取 并不能直接在创建Bean时候就能拿到
 * 所以需要在refresh操作时，把ApplicationContext写入到一个包装的BeanPostProcessor中去
 * 再由AbstractAutowireCapableBeanFactory.applyBeanPostProcessorBeforeInitialization方法调用
 * */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
