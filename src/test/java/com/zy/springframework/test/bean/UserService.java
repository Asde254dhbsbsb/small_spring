package com.zy.springframework.test.bean;

import com.zy.springframework.beans.factory.*;
import com.zy.springframework.context.ApplicationContext;
import com.zy.springframework.context.ApplicationContextAware;

/**
 * @author zy
 * @since 2022/7/23  16:42
 */
public class UserService implements /*InitializingBean, DisposableBean*/ BeanNameAware, BeanClassLoaderAware, BeanFactoryAware , ApplicationContextAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    private String uId;
    private String company;
    private String location;
//    private UserDao userDao;
    private IUserDao userDao;

    public String queryUserInfo() {
        System.out.println("查询用户信息" +
                ":" + userDao.queryUserName(uId));
        return toString();
    }

//    @Override
//    public void destroy() throws Exception {
//        System.out.println("执行：UserService.destroy");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("执行 ： UserService.afterPropertiesSet");
//    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }



    public UserService(){}

    public String getuId() {
        return uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader: " + classLoader);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean Name is: " + name);
    }
}
