package org.chenyang.spring.aop.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib动态代理
 * @author : ChenYang
 * @date : 2020-09-07 2:27 下午
 * @since :
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib proxy begin");
        Object result = method.invoke(target, objects);
        System.out.println("cglib proxy end");
        return result;
    }

    public Object getCglibProxy(Object targetObject) {
        this.target = targetObject;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetObject.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        IUserManager userManager = (IUserManager) cglibProxy.getCglibProxy(new UserManagerImpl());
        userManager.deleteUser("admin");
    }
}
