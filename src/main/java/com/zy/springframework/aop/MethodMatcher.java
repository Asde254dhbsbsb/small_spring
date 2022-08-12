package com.zy.springframework.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    /**
     * 找到表达式范围内匹配下的目标类和方法
     * */
    boolean matches(Method method, Class<?> targetClass);
}
