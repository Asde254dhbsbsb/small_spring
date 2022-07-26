package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.BeanFactory;
import com.zy.springframework.beans.factory.DisposableBean;
import com.zy.springframework.beans.factory.FactoryBean;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.zy.springframework.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zy
 * @since 2022/7/23  17:02
 */
/**
 * 继承了单例注册类，那么就有了单例Bean的注册功能
 * 对BeanFactory的实现，
 * 在getBean方法中，获取不到时需要拿到Bean定义做相应的实例化
 * 并没有自身实现这些方法，而是定义了调用过程以及提供了抽象方法  由实现此抽象类的其他类做相应调用
 * */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name , args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 对获取 FactoryBean 的操作
     * */
    public <T> T doGetBean(final String name, final Object... args) throws BeansException {
        Object sharedInstance = getSingleton(name);
        if(sharedInstance != null) {
//            如果是 FactoryBean， 则需要调用 FactoryBean # getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    public Object getObjectForBeanInstance(Object beanInstance, String beanName) throws BeansException {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance; // bean 不是FactoryBean的话
        }

        Object object = getCacheObjectForFactoryBean(beanName);
//          是FactoryBean而且 没有缓存
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException ;
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException ;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
