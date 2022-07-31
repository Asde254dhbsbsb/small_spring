package com.zy.springframework.beans.factory;

/**
 * 实现此接口，即能感知到所属的ClassLoader
 * */
public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader);
}
