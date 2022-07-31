package com.zy.springframework.beans.factory;

import java.lang.reflect.Type;

public interface FactoryBean<T>{
    T getObject();
    Class<?> getObjectType();
    boolean isSingleton();
}
