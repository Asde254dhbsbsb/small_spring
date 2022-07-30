package com.zy.springframework.beans.factory.support;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zy
 * @since 2022/7/23  17:04
 */
/**
 * 都集中了在这个类里
 * 接口定义了注册， 抽象类定义了获取 都集中在 beanDefinitionMap 里面*/
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type){
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                try {
                    result.put(beanName, (T) getBean(beanName));
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach((beanName) -> {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        });
    }

}
