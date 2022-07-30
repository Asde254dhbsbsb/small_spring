package com.zy.springframework.context.support;

/**
 * @author zy
 * @since 2022/7/27  11:33
 */

import com.zy.springframework.beans.BeansException;

/**
 * 面向用户
 * */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;

    public ClassPathXmlApplicationContext() {};

    /**
     * 从 XML 中加载BeanDefinition，并刷新上下文
     * */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
