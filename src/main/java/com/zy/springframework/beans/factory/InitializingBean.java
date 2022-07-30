package com.zy.springframework.beans.factory;

/**
 * @author zy
 * @since 2022/7/27  14:46
 */
public interface InitializingBean {

    /**
     * Bean 处理了属性填充后调用
     * */
    void afterPropertiesSet() throws Exception;
}
