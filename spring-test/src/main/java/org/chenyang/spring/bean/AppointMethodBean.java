package org.chenyang.spring.bean;

import org.springframework.context.annotation.Bean;

/**
 * @author : ChenYang
 * @date : 2020-08-25 5:35 下午
 * @since :
 */
public class AppointMethodBean {

    public void start() {
        System.out.println("AppointMethodBean start");
    }

    public void end() {
        System.out.println("AppointMethodBean end");
    }

    public void test() {
        System.out.println("AppointMethodBean test");
    }

}
