package com.zy.springframework.aop.framework.autoproxy;

import com.zy.springframework.aop.*;
import com.zy.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.zy.springframework.aop.framework.ProxyFactory;
import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.BeanFactory;
import com.zy.springframework.beans.factory.BeanFactoryAware;
import com.zy.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.zy.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 用于处理整个AOP代理融入到Bean生命周期中的核心类
 * 会依赖拦截器、代理工厂和Pointcut与Advisor的包装服务AspectJExpressionPointcutAdvisor，由它提供切面、拦截方法和表达式
 * */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessorBeforeInstantiation(Class<?> beanClass, String beanName) {
        if(isInfrastructureClass(beanClass)) return null;
        /**
         * 获取AspectJExpressionPointcutAdvisor的advisors
         * 遍历相应的AspectJExpressionPointcutAdvisor填充对应的属性信息。包括：目标对象、拦截方法、匹配器，之后返回代理对象即可
         * */
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for(AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);
            /**
             * 这个bean对象就是一个以及被切面注入的对象了
             * 会被拦截，处理用户 需要的信息；
             * */
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return null;
    }

    /**
     *
     * */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
