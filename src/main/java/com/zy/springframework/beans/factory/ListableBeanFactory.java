package com.zy.springframework.beans.factory;

import com.zy.springframework.beans.BeansException;
import java.util.Map;

/**
 * @author zy
 * @since 2022/7/23  22:52
 */
public interface ListableBeanFactory extends BeanFactory{
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
    String[] getBeanDefinitionNames();
}
