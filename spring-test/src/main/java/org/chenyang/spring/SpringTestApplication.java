package org.chenyang.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ChenYang
 * @date 2020-04-28 21:02
 **/
@SpringBootApplication
public class SpringTestApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringTestApplication.class, args);
        System.out.println("*****************SpringTestApplication启动成功***************************");
    }
}
