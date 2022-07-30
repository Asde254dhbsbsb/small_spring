package com.zy.springframework.test;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.context.support.ClassPathXmlApplicationContext;
import com.zy.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * @author zy
 * @since 2022/7/24  10:38
 */
public class XmlBeanRegistryTest {
    @Test
    public void test_xml() throws BeansException {
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//        reader.loadBeanDefinitions("classpath:spring.xml");
//
//        UserService userService = beanFactory.getBean("userService", UserService.class);
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:noclass.xml");
//        applicationContext.registerShutdownHook();
        UserService userService = (UserService)applicationContext.getBean("userService");
        String result = userService.queryUserInfo();
        System.out.println(result);

    }
}
