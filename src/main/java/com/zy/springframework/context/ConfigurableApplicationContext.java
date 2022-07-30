package com.zy.springframework.context;

import com.zy.springframework.beans.BeansException;

/**
 * @author zy
 * @since 2022/7/27  11:34
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * 刷新容器
     * */
    void refresh() throws BeansException ;

    /**
     * 定义注册虚拟机钩子的方法 和 手动执行关闭的方法
     * */
    void registerShutdownHook();

    void close() throws BeansException;
}
