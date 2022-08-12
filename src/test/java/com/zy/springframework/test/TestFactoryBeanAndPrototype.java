package com.zy.springframework.test;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.context.support.ClassPathXmlApplicationContext;
import com.zy.springframework.test.bean.UserDao;
import com.zy.springframework.test.bean.UserService;
import org.junit.Test;

public class TestFactoryBeanAndPrototype {

    @Test
    public void test_prototype() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:noclass.xml");
        applicationContext.registerShutdownHook();

        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService01.getUserDao());
        System.out.println(userService02.getUserDao());

        System.out.println(userService01 + " hash :" + Integer.toHexString(userService01.hashCode()));
        System.out.println(userService02 + " hash :" + Integer.toHexString(userService02.hashCode()));
    }

    /**
     * 测试代理对象
     *
     *
     * */
    @Test
    public void test_factory_bean() throws BeansException {
//        1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

//        2.调用代理方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println("result test : " + userService.queryUserInfo());
    }
}
