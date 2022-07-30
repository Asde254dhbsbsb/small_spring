package com.zy.springframework.context;

import com.zy.springframework.beans.factory.ListableBeanFactory;

/**
 * @author zy
 * @since 2022/7/27  11:33
 */
/**
 * 上下文接口
 * 继承了ListableBeanFactory
 * 也就继承了关于BeanFactory方法
 * 本身就是 Central接口
 * */
public interface ApplicationContext extends ListableBeanFactory {

}
