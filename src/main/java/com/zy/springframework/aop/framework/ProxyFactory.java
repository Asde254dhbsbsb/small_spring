package com.zy.springframework.aop.framework;

import com.zy.springframework.aop.AdvisedSupport;
/**
 * 解决两种代理的选择问题
 * 可以按照需求来创建
 * */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createProxy().getProxy();
    }

    private AopProxy createProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }

        return new JdkDynamicAopProxy(advisedSupport);
    }
}
