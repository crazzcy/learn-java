package org.chenyang.springboot.session.my;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.Session;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author : ChenYang
 * @date : 2021-03-22 5:10 下午
 * @since :
 */
public class MySessionExpirationPolicy {
    private final RedisTemplate<Object, Object> redis;
    private final Function<Long, String> lookupExpirationKey;
    private final Function<String, String> lookupSessionKey;

    MySessionExpirationPolicy(RedisTemplate<Object, Object> redis,
                              Function<Long, String> lookupExpirationKey,
                              Function<String, String> lookupSessionKey) {
        this.redis = redis;
        this.lookupExpirationKey = lookupExpirationKey;
        this.lookupSessionKey = lookupSessionKey;
    }

    public void onDelete(Session session) {
        long toExpire = roundUpToNextMinute(expiresInMillis(session));
        String expireKey = this.getExpirationKey(toExpire);
        this.redis.boundSetOps(expireKey).remove(session.getId());
    }

    public void onExpirationUpdated(Long originalExpirationTimeInMilli, Session session) {
        String keyToExpire = "expires:" + session.getId();
        long toExpire = roundUpToNextMinute(expiresInMillis(session));
        long sessionExpireInSeconds;
        String sessionKey;
        if (originalExpirationTimeInMilli != null) {
            sessionExpireInSeconds = roundUpToNextMinute(originalExpirationTimeInMilli);
            if (toExpire != sessionExpireInSeconds) {
                sessionKey = this.getExpirationKey(sessionExpireInSeconds);
                this.redis.boundSetOps(sessionKey).remove(keyToExpire);
            }
        }

        sessionExpireInSeconds = session.getMaxInactiveInterval().getSeconds();
        sessionKey = this.getSessionKey(keyToExpire);
        if (sessionExpireInSeconds < 0L) {
            this.redis.boundValueOps(sessionKey).append("");
            this.redis.boundValueOps(sessionKey).persist();
            this.redis.boundHashOps(this.getSessionKey(session.getId())).persist();
        } else {
            String expireKey = this.getExpirationKey(toExpire);
            BoundSetOperations<Object, Object> expireOperations = this.redis.boundSetOps(expireKey);
            expireOperations.add(keyToExpire);
            long fiveMinutesAfterExpires = sessionExpireInSeconds + TimeUnit.MINUTES.toSeconds(5L);
            expireOperations.expire(fiveMinutesAfterExpires, TimeUnit.SECONDS);
            if (sessionExpireInSeconds == 0L) {
                this.redis.delete(sessionKey);
            } else {
                this.redis.boundValueOps(sessionKey).append("");
                this.redis.boundValueOps(sessionKey).expire(sessionExpireInSeconds, TimeUnit.SECONDS);
            }

            this.redis.boundHashOps(this.getSessionKey(session.getId())).expire(fiveMinutesAfterExpires, TimeUnit.SECONDS);
        }
    }

    String getExpirationKey(long expires) {
        return this.lookupExpirationKey.apply(expires);
    }

    String getSessionKey(String sessionId) {
        return (String)this.lookupSessionKey.apply(sessionId);
    }

    public void cleanExpiredSessions() {
        long now = System.currentTimeMillis();
        long prevMin = roundDownMinute(now);

        String expirationKey = this.getExpirationKey(prevMin);
        Set<Object> sessionsToExpire = this.redis.boundSetOps(expirationKey).members();
        this.redis.delete(expirationKey);

        assert sessionsToExpire != null;
        for (Object session : sessionsToExpire) {
            String sessionKey = this.getSessionKey((String) session);
            this.touch(sessionKey);
        }

    }

    private void touch(String key) {
        this.redis.hasKey(key);
    }

    static long expiresInMillis(Session session) {
        int maxInactiveInSeconds = (int)session.getMaxInactiveInterval().getSeconds();
        long lastAccessedTimeInMillis = session.getLastAccessedTime().toEpochMilli();
        return lastAccessedTimeInMillis + TimeUnit.SECONDS.toMillis((long)maxInactiveInSeconds);
    }

    static long roundUpToNextMinute(long timeInMs) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timeInMs);
        date.add(Calendar.MINUTE, 1);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MILLISECOND);
        return date.getTimeInMillis();
    }

    static long roundDownMinute(long timeInMs) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timeInMs);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MILLISECOND);
        return date.getTimeInMillis();
    }
}
