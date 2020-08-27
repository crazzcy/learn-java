package org.chenyang.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author : ChenYang
 * @date : 2020-08-26 11:06 下午
 * @since :
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("appointMethodBean".equalsIgnoreCase(beanName)) {
            System.out.println("MyBeanPostProcessor postProcessBeforeInitialization:" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("appointMethodBean".equalsIgnoreCase(beanName)) {
            System.out.println("MyBeanPostProcessor postProcessAfterInitialization:" + beanName);
        }
        return bean;
    }
}
