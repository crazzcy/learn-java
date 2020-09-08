package org.chenyang.spring.aop.dp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk动态代理：
 * java.lang.reflect.Proxy -> 用于创建代理对象。
 * java.lang.reflect.InvocationHandler -> 处理执行逻辑
 * @author : ChenYang
 * @date : 2020-09-04 4:51 下午
 * @since :
 */
public class MyJdkHandler implements InvocationHandler {

    private Object target;

    public MyJdkHandler(Class clazz) {
        try {
            this.target = clazz.newInstance();
        } catch (InstantiationException| IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.before();
        Object result = method.invoke(target, args);
        this.after();
        return result;
    }


    private void before() {
        System.out.println("MyJdkHandler before method invoke");
    }

    private void after() {
        System.out.println("MyJdkHandler after method invoke");
    }
}
