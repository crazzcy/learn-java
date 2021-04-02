package org.chenyang.springboot.session.my;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.chenyang.springboot.session.common.SessionProperties;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

/**
 * @author : ChenYang
 * @date : 2021-03-22 5:53 下午
 * @since :
 */
//@Order(1)
//@Configuration
//@EnableScheduling
//@EnableConfigurationProperties({SessionProperties.class})
public class MySessionConfiguration extends SpringHttpSessionConfiguration implements BeanClassLoaderAware {

    private ClassLoader loader;

    @Bean
    public MySessionRepository mySessionRepository(@Autowired SessionProperties sessionProperties,
                                                 @Autowired RedisTemplate<Object, Object> redisTemplate) {
        return new MySessionRepository(redisTemplate, sessionProperties);
    }

    /**
     * Customized {@link ObjectMapper} to add mix-in for class that doesn't have default
     * constructors
     *
     * @return the {@link ObjectMapper} to use
     */
    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        return mapper;
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object, Object>  redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // jdk自带的序列化工具，不使用
        // RedisSerializer<Object> jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        RedisSerializer<Object> genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper());

        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }
}