package com.zy.springframework.beans;

/**
 * @author zy
 * @since 2022/7/23  16:58
 */
public class BeansException extends Exception{
    public BeansException() {
        super();
    }

    public BeansException(String msg, Exception e) {
        super(msg, e);
    }

    public BeansException(String msg) {
        super(msg);
    }
}
