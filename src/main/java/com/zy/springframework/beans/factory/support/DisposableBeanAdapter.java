package com.zy.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.factory.DisposableBean;
import com.zy.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author zy
 * @since 2022/7/27  15:12
 */

/**
 * 因为销毁方法有两种甚至多种方式，目前有 实现接口 DisposableBean、配置信息 destroy-method
 * 而这两种方式的销毁动作是由 AbstractApplicationContext 在注册虚拟机钩子后看，虚拟机关闭前执行的操作动作
 *
 * 那么在销毁执行时不太希望还得关注都销毁那些类型的方法，
 * 它的使用上更希望是有一个统一的接口继续销毁，所以这里新增了适配类，做统一处理
 * */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }
    @Override
    public void destroy() throws Exception {
//        1.实现接口DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

//        2.配置信息 判断
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean
                && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '"
                        + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
