package com.zy.springframework.beans.factory;

/**
 * 实现此接口，即能感知到所属的BeanName
 * */
public interface BeanNameAware extends Aware{

    void setBeanName(String name);
}
