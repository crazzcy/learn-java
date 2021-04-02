package org.chenyang.springboot.session.diy;

import org.chenyang.springboot.session.common.SessionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

/**
 * @author : ChenYang
 * @date : 2021-03-18 8:21 下午
 * @since :
 */
@Order(1)
@Configuration
@EnableScheduling
@EnableConfigurationProperties({SessionProperties.class})
public class DiyHttpSessionConfiguration extends SpringHttpSessionConfiguration {

//    @Bean
//    public DiySessionRepository diySessionProperties(@Autowired SessionProperties sessionProperties, @Autowired RedisTemplate<String, String> redisTemplate) {
//        return new DiySessionRepository(redisTemplate, sessionProperties);
//    }
}
