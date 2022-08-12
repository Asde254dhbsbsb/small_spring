package com.zy.springframework.test;

import com.zy.springframework.aop.AdvisedSupport;
import com.zy.springframework.aop.TargetSource;
import com.zy.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.zy.springframework.beans.BeansException;
import com.zy.springframework.beans.PropertyValue;
import com.zy.springframework.beans.PropertyValues;
import com.zy.springframework.beans.factory.config.BeanDefinition;
import com.zy.springframework.beans.factory.config.BeanReference;
import com.zy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.zy.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.zy.springframework.context.support.ClassPathXmlApplicationContext;
import com.zy.springframework.aop.framework.Cglib2AopProxy;
import com.zy.springframework.aop.framework.JdkDynamicAopProxy;
import com.zy.springframework.test.aoptest.IUserService;
import com.zy.springframework.test.aoptest.User1Service;
import com.zy.springframework.test.aoptest.UserServiceInterceptor;
import com.zy.springframework.test.bean.CustomEvent;
import com.zy.springframework.test.common.MyBeanFactoryPostProcessor;
import com.zy.springframework.test.common.MyBeanPostProcessor;
import org.junit.Test;
import com.zy.springframework.test.bean.UserDao;
import com.zy.springframework.test.bean.UserService;

import java.lang.reflect.Method;

/**
 * @author zy
 * @since 2022/7/23  16:41
 */
public class ApiTest {

    @Test
    public void test_event() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:listener.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 10101001l, "success"));

        applicationContext.registerShutdownHook();
    }
    @Test
    public void test_BeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        注册 bean

        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring2.xml");

        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("!!!" + result);
    }

    @Test
    public void test_aop() throws NoSuchMethodException, BeansException {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.zy.springframework.test.bean.UserService.*(..))");
//        Class<UserService> clazz = UserService.class;
//        Method method = clazz.getDeclaredMethod("queryUserInfo");
//        System.out.println(pointcut.matches(clazz));
//        System.out.println(pointcut.matches(method, clazz));
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aoparound.xml");
        IUserService user1Service = applicationContext.getBean("user1Service", IUserService.class);
        System.out.println("测试结果： " + user1Service.queryUserInfo());
    }

    @Test
    public void test_dynamic() {
        /**
         * 目标对象、怎么组装代理对象、如何调用代理对象
         * AdvisedSupport ： 包装了目标对象、用户自己实现的拦截方法以及方法匹配表达式
         * */
        IUserService service = new User1Service();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(service));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.zy.springframework.test.aoptest.IUserService.*(..))"));

        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println("test result :" + proxy_jdk.queryUserInfo());

        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println("test result :" + proxy_cglib.queryUserInfo());
    }

    @Test
    public void test_string() {
        int[] cost = {4,3,2,5,6,7,2,5,5};
        int target = 9;
        String s = largestNumber(cost, target);
        System.out.println(s);
    }

    public String largestNumber(int[] cost, int target) {
        // 最大整数 添加一个数位(i + 1) 的成本为 cost[i] 最后代价必须为 target！
        String[] dp = new String[target + 1];
        dp[0] = "";
        for(int i = 1; i <= target; i++) {
            for(int j = 0; j < 9; j++) {
                int tmp = cost[j];
                if(i >= cost[j] && dp[i-cost[j]] != null) {
                    dp[i] = makeMax(dp[i-cost[j]], (char) ('1' + j), dp[i]);
                }
            }
        }
        return dp[target] == null ? "0" : dp[target];
    }

    String makeMax(String str1, Character chr, String str2) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i <= str1.length(); i++) {
            if(i < str1.length() && str1.charAt(i) > chr) sb.append(str1.charAt(i));
            else {
                sb.append(chr);
                sb.append(str1.substring(i));
                break;
            }
        }
        str1 = sb.toString();
        if(str2 != null) {
            if(str2.length() < str1.length()) return str1;
            else if(str2.length() > str1.length()) return str2;
            else {
                for(int i = 0; i < str1.length(); i++) {
                    if(str1.charAt(i) > str2.charAt(i)) return str1;
                    if(str1.charAt(i) < str2.charAt(i)) return str2;
                }
            }
        }
        return str1;
    }

    @Test
    public void test_compareTo() {
        System.out.println("234".compareTo("1111"));
    }

}
