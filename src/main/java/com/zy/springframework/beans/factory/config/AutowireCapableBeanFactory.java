package com.zy.springframework.beans.factory.config;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.BeanFactory;

/**
 * @author zy
 * @since 2022/7/24  10:14
 */
/**
 * 是一个自动化处理Bean工厂配置的接口
 * */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
