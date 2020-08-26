package org.chenyang.spring.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author : ChenYang
 * @date : 2020-08-25 5:24 下午
 * @since :
 */
@Component
public class ImplementsBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ImplementsBean->InitializingBean afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("ImplementsBean->DisposableBean destroy");
    }
}
