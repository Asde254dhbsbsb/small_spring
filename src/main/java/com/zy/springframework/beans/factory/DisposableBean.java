package com.zy.springframework.beans.factory;

/**
 * @author zy
 * @since 2022/7/27  14:46
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
