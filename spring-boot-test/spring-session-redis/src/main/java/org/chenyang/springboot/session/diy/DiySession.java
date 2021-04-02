package org.chenyang.springboot.session.diy;

import com.alibaba.fastjson.annotation.JSONField;
import org.chenyang.springboot.session.common.SessionConstant;
import org.springframework.session.Session;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author : ChenYang
 * @date : 2021-03-18 7:20 下午
 * @since :
 */
public class DiySession implements Session, Serializable {

    /**
     * session id
     */
    private String id;

    /**
     * 创建时间
     */
    private Instant creationTime;

    /**
     * 属性
     */
    private Map<String, Object> attributes;

    /**
     * 上一次操作时间
     */
    private Instant lastAccessedTime;

    /**
     * 过期时间
     */
    private Duration maxInactiveInterval;

    public DiySession() {
        // 这里可以改成自己sessionId的生成规则
        this(UUID.randomUUID().toString().substring(8));
    }

    public DiySession(String id) {
        this.setId(id);
        this.setCreationTime(Instant.now());
        this.setMaxInactiveInterval(Duration.ofSeconds(SessionConstant.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS));
        this.setAttributes(new HashMap<>());
        this.setLastAccessedTime(this.creationTime);
    }

    public DiySession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        this.id = session.getId();
        this.setAttributes(new HashMap<>());
        session.getAttributeNames().forEach(k -> this.getAttributes().put(k, session.getAttribute(k)));
        this.lastAccessedTime = session.getLastAccessedTime();
        this.creationTime = session.getCreationTime();
        this.maxInactiveInterval = session.getMaxInactiveInterval();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public Instant getCreationTime() {
        return this.creationTime;
    }

    @Override
    public void setLastAccessedTime(Instant lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }
    @Override
    public Instant getLastAccessedTime() {
        return lastAccessedTime;
    }

    @JSONField(serialize = false, deserialize = false)
    @Override
    public Duration getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @JSONField(serialize = false, deserialize = false)
    @Override
    public void setMaxInactiveInterval(Duration interval) {

    }
    @JSONField(serialize = false, deserialize = false)
    @Override
    public boolean isExpired() {
        return isExpired(Instant.now());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String attributeName) {
        return (T) this.attributes.get(attributeName);
    }

    @JSONField(serialize = false, deserialize = false)
    @Override
    public Set<String> getAttributeNames() {
        return this.attributes.keySet();
    }

    @Override
    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeValue == null) {
            removeAttribute(attributeName);
        } else {
            this.attributes.put(attributeName, attributeValue);
        }
    }
    @Override
    public void removeAttribute(String attributeName) {
        this.attributes.remove(attributeName);
    }

    @JSONField(serialize = false, deserialize = false)
    private boolean isExpired(Instant now) {
        if (this.getMaxInactiveInterval().isNegative()) {
            return false;
        }
        return now.minus(this.getMaxInactiveInterval()).compareTo(this.getLastAccessedTime()) >= 0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
    @JSONField(serialize = false, deserialize = false)
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
