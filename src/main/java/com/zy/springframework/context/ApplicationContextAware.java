package com.zy.springframework.context;

import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.Aware;

/**
 * 实现此接口，即能感知到所属的ApplicationContext
 * */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
