package org.chenyang.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring生命周期， 先执行InitializingBean.afterPropertiesSet， 再执行init-method方法
 * @author : ChenYang
 * @date : 2020-08-25 5:35 下午
 * @since :
 */
public class TestSpringBeanLifeCycle implements InitializingBean, DisposableBean, ApplicationContextAware, BeanNameAware {

    private ApplicationContext applicationContext;

    private String beanName;

    /**
     * init method
     */
    public void start() {
        System.out.println("4、SpringLifeCycleTestBean custom init-method");
    }

    public void end() {
        System.out.println("7、SpringLifeCycleTestBean custom destroy-method");
    }

    public void test() {
        System.out.println("5、SpringLifeCycleTestBean test");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("3、InitializingBean->SpringLifeCycleTestBean::afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("6、DisposableBean->SpringLifeCycleTestBean::destroy");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("2、SpringLifeCycleTestBean::setApplicationContext");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("1、SpringLifeCycleTestBean::setBeanName");
    }
}
