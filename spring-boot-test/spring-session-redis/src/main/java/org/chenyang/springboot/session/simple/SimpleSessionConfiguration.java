package org.chenyang.springboot.session.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.chenyang.springboot.session.common.SessionProperties;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.MapSession;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

/**
 * @author : ChenYang
 * @date : 2021-04-01 3:42 下午
 * @since :
 */
@Order(1)
@Configuration
@EnableScheduling
@EnableConfigurationProperties({SessionProperties.class})
public class SimpleSessionConfiguration extends SpringHttpSessionConfiguration implements BeanClassLoaderAware {
    private ClassLoader loader;

    @Bean
    public SimpleSessionRepository simpleSessionRepository(@Autowired SessionProperties sessionProperties,
                                                   @Autowired RedisTemplate<Object, Object> redisTemplate) {
        return new SimpleSessionRepository(redisTemplate, sessionProperties);
    }

    @Bean
    @Scope("prototype")
    public MapSession mapSession() {
        return new MapSession();
    }

    /**
     * Customized {@link ObjectMapper} to add mix-in for class that doesn't have default
     * constructors
     * @return the {@link ObjectMapper} to use
     */
    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(new NamedType(MapSession.class, "MapSession"));
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        return mapper;
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object, Object>  redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // jdk自带的序列化工具，不使用
        RedisSerializer<Object> jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
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
