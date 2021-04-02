package org.chenyang.springboot.session.simple;

import org.chenyang.springboot.session.common.SessionProperties;
import org.chenyang.springboot.session.util.SpringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author : ChenYang
 * @date : 2021-04-01 3:40 下午
 * @since :
 */
public class SimpleSessionRepository implements SessionRepository<MapSession> {

    private final RedisTemplate<Object, Object> redisTemplate;

    private String namespace = "simple:session:";

    private RedisFlushMode redisFlushMode;

    private Integer timeout;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public RedisFlushMode getRedisFlushMode() {
        return redisFlushMode;
    }

    public void setRedisFlushMode(RedisFlushMode redisFlushMode) {
        this.redisFlushMode = redisFlushMode;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public SimpleSessionRepository(RedisTemplate<Object, Object> redisTemplate, SessionProperties sessionProperties) {
        Assert.notNull(redisTemplate, "redisTemplate cannot be null");
        this.redisTemplate = redisTemplate;
        if (sessionProperties.getNamespace() != null && !"".equals(sessionProperties.getNamespace())) {
            setNamespace(sessionProperties.getNamespace() + ":");
        }
        if("on_save".equals(sessionProperties.getFlushMode())) {
            this.setRedisFlushMode(RedisFlushMode.ON_SAVE);
        } else {
            this.setRedisFlushMode(RedisFlushMode.IMMEDIATE);
        }
        setTimeout((int)sessionProperties.getTimeout().getSeconds());
    }

    @Override
    public MapSession createSession() {
        MapSession session = SpringUtil.getBean(MapSession.class);
        if (this.getTimeout() != null) {
            session.setMaxInactiveInterval(Duration.ofSeconds(this.getTimeout()));
        }
        return session;
    }

    @Override
    public void save(MapSession session) {
        redisTemplate.opsForValue().set(getSessionKey(session.getId()), session, timeout, TimeUnit.SECONDS);
    }

    @Override
    public MapSession findById(String id) {
        redisTemplate.expire(getSessionKey(id), timeout, TimeUnit.SECONDS);
        Object s = redisTemplate.opsForValue().get(getSessionKey(id));
        return (MapSession)s;
    }

    @Override
    public void deleteById(String id) {
        redisTemplate.delete(getSessionKey(id));
    }


    private String getSessionKey(String sessionId) {
        return this.getNamespace() + sessionId;
    }
}
