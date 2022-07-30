package com.zy.springframework.context.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.zy.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author zy
 * @since 2022/7/27  11:32
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) throws BeansException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 从入口上下文类，拿到配置信息的地址描述
     * */
    protected abstract String[] getConfigLocations();
}
