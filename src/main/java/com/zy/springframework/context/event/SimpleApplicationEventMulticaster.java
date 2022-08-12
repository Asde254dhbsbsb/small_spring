package com.zy.springframework.context.event;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.BeanFactory;
import com.zy.springframework.context.ApplicationEvent;
import com.zy.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) throws BeansException {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) throws BeansException {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
