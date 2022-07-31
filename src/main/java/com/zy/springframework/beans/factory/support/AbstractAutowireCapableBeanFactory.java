package com.zy.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.PropertyValue;
import com.zy.springframework.beans.PropertyValues;
import com.zy.springframework.beans.factory.*;
import com.zy.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.beans.factory.config.BeanReference;
import com.zy.springframework.beans.factory.support.instantiate.CglibSubclassingInstantiationStrategy;
import com.zy.springframework.beans.factory.support.instantiate.InstantiationStrategy;
import com.zy.springframework.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author zy
 * @since 2022/7/23  17:02
 */
/**
 * 实现了Bean的实例化操作 newInstance
 * 但是有构造函数入参的对象怎么处理呢？
 * */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
//            给Bean填充属性
            applyProperValues(beanName, bean, beanDefinition);
//            执行初始化方法和BeanPostProcessor的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
//      注册实现了 DisposableBean 接口的Bean对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        /**
         * 是否单列取决于是否放入内存
         * */
        if(beanDefinition.isSingleton()) addSingleton(beanName, bean);
        return bean;
    }


    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
//        invokeAwareMethods
        /**
         * 通知已经实现此接口的类
         * */
        if (bean instanceof Aware) {
            if(bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if(bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if(bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
//      执行Bean 对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        /**
         * 非Singleton类型的Bean不执行销毁方法
         * */
        if(!beanDefinition.isSingleton()) return ;
        if (bean instanceof DisposableBean ||
                StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
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


    /**
     * 初始化方法
     * 使用者可以在initMethod里面额外新增自己想要的动作
     * */
    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
//        1.实现接口
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }
//        2.配置信息 init-method(判断是为了避免二次执行销毁)
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
//            获取初始化方法名
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
//            初始化!
            initMethod.invoke(wrappedBean);
        }
    }
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessorAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;

    }
}
