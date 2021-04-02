package org.chenyang.springboot.session.my;

import org.chenyang.springboot.session.common.SessionProperties;
import org.chenyang.springboot.session.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * @author : ChenYang
 * @date : 2021-03-19 5:40 下午
 * @since :
 */
public class MySessionRepository implements SessionRepository<MySession> {

    private static final Logger logger = LoggerFactory.getLogger(MySessionRepository.class);


    private final RedisTemplate<Object, Object> redisTemplate;

    /**
     * redis key 命名空间，key前缀
     */
    private String namespace = "spring:session:";
    /**
     * 创建channel的前缀名称
     */
    private Integer defaultMaxInactiveInterval;

    private int database = 0;

    private String sessionCreatedChannelPrefix;

    private final MySessionExpirationPolicy expirationPolicy;

    private RedisFlushMode redisFlushMode;

    private final MyPrincipalNameResolver principalNameResolver = new MyPrincipalNameResolver();

    public MySessionRepository(RedisTemplate<Object, Object> redisTemplate, SessionProperties sessionProperties) {
        Assert.notNull(redisTemplate, "redisTemplate cannot be null");
        this.redisTemplate = redisTemplate;
        this.expirationPolicy = new MySessionExpirationPolicy(redisTemplate, this::getExpirationsKey, this::getSessionKey);
        if (sessionProperties.getNamespace() != null && !"".equals(sessionProperties.getNamespace())) {
            this.namespace = sessionProperties.getNamespace() + ":";
        }
        if("on_save".equals(sessionProperties.getFlushMode())) {
            this.setRedisFlushMode(RedisFlushMode.ON_SAVE);
        } else {
            this.setRedisFlushMode(RedisFlushMode.IMMEDIATE);
        }
        setDefaultMaxInactiveInterval((int)sessionProperties.getTimeout().getSeconds());
    }

    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    public RedisFlushMode getRedisFlushMode() {
        return redisFlushMode;
    }

    public void setRedisFlushMode(RedisFlushMode redisFlushMode) {
        this.redisFlushMode = redisFlushMode;
    }

//    public void setDatabase(int database) {
//        this.database = database;
//        this.configureSessionChannels();
//    }

//    private void configureSessionChannels() {
////        this.sessionCreatedChannelPrefix = this.namespace + "event:" + this.database + ":created:";
////        this.sessionDeletedChannel = "__keyevent@" + this.database + "__:del";
////        this.sessionExpiredChannel = "__keyevent@" + this.database + "__:expired";
//    }

    private String getSessionCreatedChannel(String sessionId) {
        return this.getSessionCreatedChannelPrefix() + sessionId;
    }

    public String getSessionCreatedChannelPrefix() {
        return this.sessionCreatedChannelPrefix;
    }
    public String getSessionKey(String sessionId) {
        return this.namespace + "sessions:" + sessionId;
    }

    public String getExpirationsKey(long expiration) {
        return this.namespace + "expirations:" + expiration;
    }
    public String getSessionAttrNameKey(String attributeName) {
        return "sessionAttr:" + attributeName;
    }

    public BoundHashOperations<Object, Object, Object> getSessionBoundHashOperations(String sessionId) {
        String key = this.getSessionKey(sessionId);
        return this.redisTemplate.boundHashOps(key);
    }

    public RedisTemplate<Object, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    public MySessionExpirationPolicy getExpirationPolicy() {
        return this.expirationPolicy;
    }

    public MyPrincipalNameResolver getPrincipalNameResolver() {
        return this.principalNameResolver;
    }

    /**
     * 获取过期的redis key
     * @param sessionId 会话id
     * @return redis key
     */
    public String getExpiredKey(String sessionId) {
        return this.getExpiredKeyPrefix() + sessionId;
    }

    /**
     * 获取过期的redis key前缀
     * @return redis key前缀
     */
    public String getExpiredKeyPrefix() {
        return this.namespace + "sessions:expires:";
    }

    public String getPrincipalKey(String principalName) {
        return this.namespace + "index:" + FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME + ":" + principalName;
    }

    private MapSession loadSession(String id, Map<Object, Object> entries) {
        MapSession loaded = new MapSession(id);

        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            String key = (String) entry.getKey();
            if ("creationTime".equals(key)) {
                loaded.setCreationTime(Instant.ofEpochMilli((Long) entry.getValue()));
            } else if ("maxInactiveInterval".equals(key)) {
                loaded.setMaxInactiveInterval(Duration.ofSeconds((long) (Integer) entry.getValue()));
            } else if ("lastAccessedTime".equals(key)) {
                loaded.setLastAccessedTime(Instant.ofEpochMilli((Long) entry.getValue()));
            } else if (key.startsWith("sessionAttr:")) {
                loaded.setAttribute(key.substring("sessionAttr:".length()), entry.getValue());
            }
        }
        return loaded;
    }

    @Override
    public MySession createSession() {
        MySession mySession = SpringUtil.getBean(MySession.class);
        if (this.defaultMaxInactiveInterval != null) {
            mySession.setMaxInactiveInterval(Duration.ofSeconds((long)this.defaultMaxInactiveInterval));
        }
        return mySession;
    }

    @Override
    public void save(MySession session) {
        session.saveDelta();
        if (session.isNew()) {
            String sessionCreatedKey = this.getSessionCreatedChannel(session.getId());
            redisTemplate.convertAndSend(sessionCreatedKey, session.getDelta());
            session.setNew(false);
        }
    }

    @Override
    public MySession findById(String sessionId) {
        return this.getSession(sessionId, false);
    }

    @Override
    public void deleteById(String sessionId) {
        MySession session = this.getSession(sessionId, true);
        if (session != null) {
            // 删除索引
            this.cleanupPrincipalIndex(session);
            this.expirationPolicy.onDelete(session);
            String expireKey = this.getExpiredKey(session.getId());
            this.redisTemplate.delete(expireKey);
            session.setMaxInactiveInterval(Duration.ZERO);
            // redis源码里是通过CRON表达式定期清理，我这边先写成直接清
            this.cleanupExpiredSessions();
            this.save(session);
        }
    }

    /**
     * 从redis缓存中取到会话实体信息
     * @param sessionId 会话id
     * @param allowExpired 是否过期
     * @return
     */
    private MySession getSession(String sessionId, boolean allowExpired) {
        String key = this.getSessionKey(sessionId);
        Map<Object, Object> entries = this.redisTemplate.boundHashOps(key).entries();
        if (entries == null || entries.isEmpty()) {
            return null;
        } else {
            MapSession loaded = this.loadSession(sessionId, entries);
            if (!allowExpired && loaded.isExpired()) {
                return null;
            } else {
                MySession result = new MySession(loaded);
                result.setOriginalLastAccessTime(loaded.getLastAccessedTime());
                return result;
            }
        }
    }

    public void cleanupExpiredSessions() {
        this.expirationPolicy.cleanExpiredSessions();
    }

    /**
     * 清除主体索引
     * @param session
     */
    private void cleanupPrincipalIndex(MySession session) {
        String sessionId = session.getId();
        String principal = principalNameResolver.resolvePrincipal(session);
        String principalKey = this.getPrincipalKey(principal);
        if (principal != null) {
            this.redisTemplate.boundSetOps(principalKey).remove(sessionId);
        }
    }
}
