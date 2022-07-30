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

    /**
     * 为了在xml配置的对象里面，可以配置init-method等方法
     * 最终实现接口的效果是一样的
     * 只不过一个是接口方法的直接调用
     * 另外是一个在配置文件中读取到方法反射调用
     * */
    private String initMethodName;

    private String destroyMethodName;

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

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
