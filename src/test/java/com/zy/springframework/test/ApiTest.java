package com.zy.springframework.test;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.PropertyValue;
import com.zy.springframework.beans.PropertyValues;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanReference;
import com.zy.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;
import com.zy.springframework.test.bean.UserDao;
import com.zy.springframework.test.bean.UserService;

/**
 * @author zy
 * @since 2022/7/23  16:41
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        注册 bean

        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
