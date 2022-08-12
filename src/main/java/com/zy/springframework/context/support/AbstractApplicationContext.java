package com.zy.springframework.context.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.zy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.zy.springframework.context.ApplicationEvent;
import com.zy.springframework.context.ApplicationListener;
import com.zy.springframework.context.ConfigurableApplicationContext;
import com.zy.springframework.context.event.ApplicationEventMulticaster;
import com.zy.springframework.context.event.ContextClosedEvent;
import com.zy.springframework.context.event.ContextRefreshedEvent;
import com.zy.springframework.context.event.SimpleApplicationEventMulticaster;
import com.zy.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
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

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;
    @Override
    public void refresh() throws BeansException {
//        1.创建 BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
//        2.获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
//        添加 ApplicationContextAwareProcessor，让继承
//        ApplicationContextAware的 Bean对象都能感知所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
//        3.在Bean实例化之前，执行
//        BeanFactoryPostProcessor(invoke factory processors registered as beans in the context)
        invokeBeanFactoryPostProcessors(beanFactory);
//        4.BeanPostProcessor 需要提前于其他Bean对象实例花之前注册
        registerBeanPostProcessor(beanFactory);
//        5. 提取实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
//        6. 初始化事件发布者
        initApplicationEventMulticaster();
//        7. 注册事件监听器
        registerListeners();
//        8. 发布容器刷新完成事件
        finishRefresh();
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
    /**
     * 实例化一个SimpleApplicationEventMulticaster
     * 这是一个事件广播器
     * */
    private void initApplicationEventMulticaster() throws BeansException {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        ((DefaultListableBeanFactory) beanFactory).registerSingleton
                (APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }
    /**
     * 注册事件监听器，获取所有加载到的事件对象
     * */
    private void registerListeners() throws BeansException {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }
    /**
     * 发布第一个服务器启动后的事件
     * */
    private void finishRefresh() throws BeansException {
        publishEvent(new ContextRefreshedEvent(this));
    }

    public void publishEvent(ApplicationEvent event) throws BeansException {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void registerShutdownHook() {
//        注册钩子----调用销毁方法
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                close();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void close() throws BeansException {
//        发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
//        执行销毁单例Bean的销毁方法
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
