package com.zy.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.PropertyValue;
import com.zy.springframework.beans.PropertyValues;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanReference;
import java.lang.reflect.Constructor;

/**
 * @author zy
 * @since 2022/7/23  17:02
 */
/**
 * 实现了Bean的实例化操作 newInstance
 * 但是有构造函数入参的对象怎么处理呢？
 * */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
//            给Bean填充属性
            applyProperValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * bean属性填充；
     * */
    protected void applyProperValues(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue pv : propertyValues.getPropertyValues()) {

                String name = pv.getName();
                Object value = pv.getValue();

                if (value instanceof BeanReference) {
//                    A 依赖 B ，获取 B 的实例化  -----  是类型引用的话获取：BeanReference
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                /**
                 * 属性填充
                 * */
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    protected  Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeansException {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        /**
         * 获取了beanClass的所有构造函数后
         * 只是数量对比，应该还有类型对比，否则参数类型不同将抛出异常
         * */
        for(Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
