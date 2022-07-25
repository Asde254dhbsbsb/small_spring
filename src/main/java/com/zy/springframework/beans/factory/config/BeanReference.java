package com.zy.springframework.beans.factory.config;

/**
 * @author zy
 * @since 2022/7/23  20:54
 */
public class BeanReference {
    private String beanName;
    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
