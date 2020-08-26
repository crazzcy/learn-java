package org.chenyang.spring.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : ChenYang
 * @date : 2020-08-25 5:32 下午
 * @since :
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBeanLifeCycle {

    @Autowired
    AnnotationBean annotationBean;

    @Autowired
    ImplementsBean implementsBean;

    @Autowired
    SpringBeanCycleConfig springBeanCycleConfig;

    @Test
    public void test() {
        System.out.println("test start");
        springBeanCycleConfig.getAppointMethodBean();
        System.out.println("test end");
    }

}
