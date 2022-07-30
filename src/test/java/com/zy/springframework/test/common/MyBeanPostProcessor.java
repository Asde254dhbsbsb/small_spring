package com.zy.springframework.test.common;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.config.BeanPostProcessor;
import com.zy.springframework.test.bean.UserService;

/**
 * @author zy
 * @since 2022/7/27  13:57
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        if("userDao".equals(beanName))System.out.println("la la la!!!");
        return bean;
    }
}
