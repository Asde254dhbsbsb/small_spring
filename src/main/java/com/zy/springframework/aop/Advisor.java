package com.zy.springframework.aop;
/**
 * 承担了Pointcut和Advice的组合
 * Pointcut用于获取JoinPoint
 * 而Advice决定了JoinPoint执行什么操作
 * */
public interface Advisor {

    Advice getAdvice();
}
