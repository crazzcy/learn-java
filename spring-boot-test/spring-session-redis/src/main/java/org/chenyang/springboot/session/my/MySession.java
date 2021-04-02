package org.chenyang.springboot.session.my;

import org.chenyang.springboot.session.util.SpringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : ChenYang
 * @date : 2021-03-19 5:15 下午
 * @since :
 */

@Component
@Scope("prototype")
public class MySession implements Session {

    private final MapSession cached;
    private Instant originalLastAccessTime;
    private Map<String, Object> delta;
    private boolean isNew;
    private String originalPrincipalName;
    private String originalSessionId;

    private final MySessionRepository mySessionRepository;

    MySession() {
        this(new MapSession());
        this.delta.put("creationTime", this.getCreationTime().toEpochMilli());
        this.delta.put("maxInactiveInterval", (int)this.getMaxInactiveInterval().getSeconds());
        this.delta.put("lastAccessedTime", this.getLastAccessedTime().toEpochMilli());
        this.isNew = true;
        this.flushImmediateIfNecessary();
    }

    MySession(MapSession cached) {
        this.mySessionRepository = SpringUtil.getBean(MySessionRepository.class);
        this.delta = new HashMap<>();
        Assert.notNull(cached, "MapSession cannot be null");
        this.cached = cached;
        this.originalPrincipalName = mySessionRepository.getPrincipalNameResolver().resolvePrincipal(this);
        this.originalSessionId = cached.getId();
    }



    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Map<String, Object> getDelta() {
        return this.delta;
    }

    public Instant getOriginalLastAccessTime() {
        return originalLastAccessTime;
    }

    public void setOriginalLastAccessTime(Instant originalLastAccessTime) {
        this.originalLastAccessTime = originalLastAccessTime;
    }

    @Override
    public void setLastAccessedTime(Instant lastAccessedTime) {
        this.cached.setLastAccessedTime(lastAccessedTime);
        this.putAndFlush("lastAccessedTime", this.getLastAccessedTime().toEpochMilli());
    }
    @Override
    public boolean isExpired() {
        return this.cached.isExpired();
    }

    public boolean isNew() {
        return this.isNew;
    }

    @Override
    public Instant getCreationTime() {
        return this.cached.getCreationTime();
    }

    @Override
    public String getId() {
        return this.cached.getId();
    }

    @Override
    public String changeSessionId() {
        return this.cached.changeSessionId();
    }

    @Override
    public Instant getLastAccessedTime() {
        return this.cached.getLastAccessedTime();
    }

    @Override
    public void setMaxInactiveInterval(Duration interval) {
        this.cached.setMaxInactiveInterval(interval);
        this.putAndFlush("maxInactiveInterval", (int)this.getMaxInactiveInterval().getSeconds());
    }

    @Override
    public Duration getMaxInactiveInterval() {
        return this.cached.getMaxInactiveInterval();
    }

    @Override
    public <T> T getAttribute(String attributeName) {
        return this.cached.getAttribute(attributeName);
    }

    @Override
    public Set<String> getAttributeNames() {
        return this.cached.getAttributeNames();
    }

    @Override
    public void setAttribute(String attributeName, Object attributeValue) {
        this.cached.setAttribute(attributeName, attributeValue);
        this.putAndFlush(mySessionRepository.getSessionAttrNameKey(attributeName), attributeValue);
    }

    @Override
    public void removeAttribute(String attributeName) {
        this.cached.removeAttribute(attributeName);
        this.putAndFlush(mySessionRepository.getSessionAttrNameKey(attributeName), null);
    }

    private void flushImmediateIfNecessary() {
        if (mySessionRepository.getRedisFlushMode() == RedisFlushMode.IMMEDIATE) {
            this.saveDelta();
        }
    }

    private void putAndFlush(String a, Object v) {
        this.delta.put(a, v);
        this.flushImmediateIfNecessary();
    }

    public void saveDelta() {
        String sessionId = this.getId();
        this.saveChangeSessionId(sessionId);
        if (!this.delta.isEmpty()) {
            this.mySessionRepository.getSessionBoundHashOperations(sessionId).putAll(this.delta);
            String principalSessionKey = this.mySessionRepository.getSessionAttrNameKey(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
            String securityPrincipalSessionKey = this.mySessionRepository.getSessionAttrNameKey("SPRING_SECURITY_CONTEXT");
            if (this.delta.containsKey(principalSessionKey) || this.delta.containsKey(securityPrincipalSessionKey)) {
                String principal;
                if (this.originalPrincipalName != null) {
                    principal = this.mySessionRepository.getPrincipalKey(this.originalPrincipalName);
                    this.mySessionRepository.getRedisTemplate().boundSetOps(principal).remove(sessionId);
                }

                principal = mySessionRepository.getPrincipalNameResolver().resolvePrincipal(this);
                this.originalPrincipalName = principal;
                if (principal != null) {
                    String principalRedisKey = mySessionRepository.getPrincipalKey(principal);
                    mySessionRepository.getRedisTemplate().boundSetOps(principalRedisKey).add(sessionId);
                }
            }
            this.delta = new HashMap<>(this.delta.size());
            Long originalExpiration = this.getOriginalLastAccessTime() != null ? this.getOriginalLastAccessTime().plus(this.getMaxInactiveInterval()).toEpochMilli() : null;
            mySessionRepository.getExpirationPolicy().onExpirationUpdated(originalExpiration, this);
        }
    }

    private void saveChangeSessionId(String sessionId) {
        if (!sessionId.equals(this.originalSessionId)) {
            if (!this.isNew()) {
                String originalSessionIdKey = mySessionRepository.getSessionKey(this.originalSessionId);
                String sessionIdKey = mySessionRepository.getSessionKey(sessionId);

                try {
                    mySessionRepository.getRedisTemplate().rename(originalSessionIdKey, sessionIdKey);
                } catch (NonTransientDataAccessException var8) {
                    this.handleErrNoSuchKeyError(var8);
                }

                String originalExpiredKey = mySessionRepository.getExpiredKey(this.originalSessionId);
                String expiredKey = mySessionRepository.getExpiredKey(sessionId);

                try {
                    mySessionRepository.getRedisTemplate().rename(originalExpiredKey, expiredKey);
                } catch (NonTransientDataAccessException var7) {
                    this.handleErrNoSuchKeyError(var7);
                }
            }
            this.originalSessionId = sessionId;
        }
    }

    private void handleErrNoSuchKeyError(NonTransientDataAccessException ex) {
        if (!"ERR no such key".equals(NestedExceptionUtils.getMostSpecificCause(ex).getMessage())) {
            throw ex;
        }
    }
}
