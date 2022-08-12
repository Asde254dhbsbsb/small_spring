package com.zy.springframework.test.bean;

import com.zy.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() {
        InvocationHandler handler = (proxy, method, args) -> {
            // 添加排除方法
            if ("toString".equals(method.getName())) return this.toString();

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "xiao fu ge");
            hashMap.put("10002", "ba bei shui");
            hashMap.put("10003", "a mao");

            return "ni bei dai li le" + method.getName() + ":" +
                    hashMap.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
