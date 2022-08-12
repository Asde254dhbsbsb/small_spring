package com.zy.springframework.aop;
/**
 * 切入点接口
 * 获取切点表达式内容
 * */
public interface Pointcut {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
