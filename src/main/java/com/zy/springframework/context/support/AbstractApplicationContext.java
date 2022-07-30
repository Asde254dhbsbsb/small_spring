package com.zy.springframework.context.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.zy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.context.ConfigurableApplicationContext;
import com.zy.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @author zy
 * @since 2022/7/27  11:32
 */
/**
 * 应用上下文抽象类实现
 * 继承DefaultResourceLoader是为了处理xml配置资源的加载
 * refresh()方法
 *
 * */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
//        1.创建 BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
//        2.获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
//        3.在Bean实例化之前，执行BeanFactoryPostProcessor(invoke factory processors registered as beans in the context)
        invokeBeanFactoryPostProcessors(beanFactory);
//        4.BeanPostProcessor 需要提前于其他Bean对象实例花之前注册
        registerBeanPostProcessor(beanFactory);
//        5. 提取实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }



    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor:
             beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }
    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public void registerShutdownHook() {
//        注册钩子----调用销毁方法
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close(){
//        关闭
        System.out.println(" close! ");
        getBeanFactory().destroySingletons();
    }

    // ...getBean、getBeansOfType、getBeanDefinitionNames 方法
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }
}
