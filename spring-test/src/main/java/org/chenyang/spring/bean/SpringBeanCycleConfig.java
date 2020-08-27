package org.chenyang.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : ChenYang
 * @date : 2020-08-25 5:45 下午
 * @since :
 */
@Configuration
public class SpringBeanCycleConfig {

    @Bean(initMethod = "start", destroyMethod = "end", value = "appointMethodBean")
    public TestSpringBeanLifeCycle getBean() {
        return new TestSpringBeanLifeCycle();
    }
}
