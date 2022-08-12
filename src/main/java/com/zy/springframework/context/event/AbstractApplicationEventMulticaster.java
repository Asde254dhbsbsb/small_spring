package com.zy.springframework.context.event;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.BeanFactory;
import com.zy.springframework.beans.factory.BeanFactoryAware;
import com.zy.springframework.context.ApplicationEvent;
import com.zy.springframework.context.ApplicationListener;
import com.zy.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
/**
 * 对事件广播器的公用方法提取；
 * */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove((ApplicationListener<ApplicationEvent>) listener);
    }
    /**
     * 主要是摘取符合广播事件中的监听处理器，具体过滤动作在supportEvent方法中
     * */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) throws BeansException {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for(ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if(supportEvent(listener, event)) allListeners.add(listener);
        }
        return allListeners;
    }

    /**
     * 监听器是否对该事件感兴趣
     * 主要对Cglib、Simple不同实例化需要获取目标Class，Cglib代理类需要获取父类的Class，
     * 普通实例化不需要。接下来就是通过提取接口和对应的 ParameterizedType 和 eventClassName
     * 方便最后确认是否为子类和父类的关系，以此证明此事件归这个符合的类处理。
     * */
    protected boolean supportEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) throws BeansException {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 按照CglibSubclassingInstantiationStrategy、
        // SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];
//      找到事件类名
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        /**
         * 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass()
         * 参数所表示的类或接口是否相同，或是否是其超类或超接口
         *
         * isAssignableFrom 是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，
         * 默认所有类的终极父类是Object。如果A.isAssignableFrom(B)结果是true，
         * 证明B可以转换成为A，也就是A可以由B转换而来
         * */
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
