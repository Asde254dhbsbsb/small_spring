package com.zy.springframework.aop;

public interface ClassFilter {
    /**
     * 用于切点找到给的的接口或目标类
     * */
    boolean matches(Class<?> clazz);
}
