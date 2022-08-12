package com.zy.springframework.test.aoptest;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start = System.currentTimeMillis();
        try{
            return methodInvocation.proceed();
        } finally {
            System.out.println("monitor-Begin By Aop");
            System.out.println("method name " + methodInvocation.getMethod());
            System.out.println("cost time " + (System.currentTimeMillis() - start) + "ms" );
            System.out.println("monitor -End\r\n");
        }
    }
}
