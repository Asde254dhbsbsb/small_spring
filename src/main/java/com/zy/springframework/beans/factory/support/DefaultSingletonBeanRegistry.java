package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.DisposableBean;
import com.zy.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.zy.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zy
 * @since 2022/7/23  17:01
 */
/**
 * ConfigurableBeanFactory定义的方法 AbstractBeanFactory继承由 ABF的父类DefaultSingletonBeanRegistry 实现
 * */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();
    /**
     * 单例对象缓存
     * */
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 实现一个受保护的添加单例对象的方法，可以被继承此类的其他类调用， 如AbstractBeanFactory等
     * */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    protected void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }
    /**
     * 销毁单例方法
     * 所有的都销毁！
     * */
    public void destroySingletons(){
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
//                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
//                throw new Exception();
            }
        }
    }
}
