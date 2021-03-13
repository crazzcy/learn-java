package org.chenyang.springboot.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author : ChenYang
 * @date : 2021-03-13 11:49 下午
 * @since :
 */
@SpringBootApplication
public class SpringTestApplication implements CommandLineRunner {

    @Resource
    private Run run;

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }

    @Override
    public void run(String... args) {
        run.printHtml();

    }
}
