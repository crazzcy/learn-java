package org.chenyang.springboot.session.diy;

import com.alibaba.fastjson.JSON;
import org.chenyang.springboot.session.common.SessionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.SessionRepository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * session持久化
 * @author : ChenYang
 * @date : 2021-03-18 7:34 下午
 * @since :
 */
public class DiySessionRepository implements SessionRepository<DiySession> {

    private static final Logger logger = LoggerFactory.getLogger(DiySessionRepository.class);

    private SessionProperties sessionProperties;

    private RedisTemplate<String, String> redisTemplate;


    public DiySessionRepository(RedisTemplate<String, String> redisTemplate,
                                SessionProperties sessionProperties) {
        this.redisTemplate = redisTemplate;
        this.sessionProperties = sessionProperties;
    }

    @Override
    public DiySession createSession() {
        DiySession session = new DiySession();
        session.setMaxInactiveInterval(sessionProperties.getTimeout());
        logger.debug("create session value = " + JSON.toJSONString(session));
        return session;
    }

    @Override
    public void save(DiySession session) {
        if(session != null) {
            String redisKey = sessionProperties.getNamespace() + session.getId();
            String sessionJson = JSON.toJSONString(session);
            logger.debug("put session:key=[" + redisKey + "] to Redis...");
            BoundValueOperations<String,String> boundValueOperations = redisTemplate.boundValueOps(session.getId());
            boundValueOperations.set(sessionJson, sessionProperties.getTimeout().getSeconds(), TimeUnit.SECONDS);
        }
    }

    @Override
    public DiySession findById(String id) {
        String redisKey = sessionProperties.getNamespace() + id;
        BoundValueOperations<String,String> boundValueOps = redisTemplate.boundValueOps(id);
        Object s = boundValueOps.get();
        if (s == null) {
            return null;
        }
        logger.debug("get session:key=[" + redisKey + "] from Redis...");
        try {
            DiySession session = JSON.parseObject(s.toString(), DiySession.class);
            if (session == null) {
                return null;
            }
            // session过期交给redis判断，能取到的都是有效的
//            if (session.isExpired()) {
//                return null;
//            }
            session.setLastAccessedTime(Instant.now());
            return session;
        } catch (Exception e) {
            logger.error("获取session异常", e);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        DiySession session = findById(id);
        if (session != null) {
            redisTemplate.delete(id);
        }
    }
}
