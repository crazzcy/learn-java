package org.chenyang.spring.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author : ChenYang
 * @date : 2020-08-25 5:28 下午
 * @since :
 */
//@Component
public class AnnotationBean {
    @PostConstruct
    public void init() {
        System.out.println("AnnotationBean init");
    }

    @PreDestroy
    public void preDestory() {
        System.out.println("AnnotationBean preDestory");
    }
}
