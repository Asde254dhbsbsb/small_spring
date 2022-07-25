package com.zy.springframework.beans.factory.config;

import com.zy.springframework.beans.PropertyValues;

/**
 * @author zy
 * @since 2022/7/23  17:00
 */
public class BeanDefinition {

    private Class beanClass;
    /**
     * 传递bean的属性信息
     * 同时做了初始化，防止需要判断为空
     * */
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

}
