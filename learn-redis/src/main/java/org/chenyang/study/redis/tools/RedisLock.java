package org.chenyang.study.redis.tools;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * Redis锁
 * @author : ChenYang
 * @date : 2020-05-27 2:51 下午
 * @since :
 */
public class RedisLock {

    private static final String REDIS_LOCK_PREFIX  = "redis_lock:";

    /**
     * key的过期时间，30秒
     */
    private static final long EXPIRED_TIME = 30 * 1000;

    /**
     * 重试锁的超时时间，默认为5秒
     */
    private static final long DEFAULT_TRY_SIMPLE_LOCK_TIMEOUT = 5 * 1000;

    /**
     * jedis set返回成功常量
     */
    private static final String TRY_LOCK_SUCCESS = "OK";

    /**
     * 解锁lua脚本返回成功常量
     */
    private static final String UNLOCK_SUCCESS = "1";

    /**
     * setnx params
     */
    private static final SetParams SET_PARAMS = SetParams.setParams().nx().px(EXPIRED_TIME);


    /**
     * redis简单加锁，单机版，指定尝试超时时间(单位毫秒)
     * 缺点：不具备可重入性
     * @param jedis jedis客户端
     * @param lockObject 要加锁的对象
     * @param timeout 尝试加锁的超时时间
     * @return 是否加锁成功
     */
    public boolean trySimpleLock(Jedis jedis, Object lockObject, Long timeout) {
        try {
            int objectHash = lockObject == null ? -1 : lockObject.hashCode();
            String lockKey = REDIS_LOCK_PREFIX + objectHash;
            long start = System.currentTimeMillis();
            while(true) {
                String result = jedis.set(lockKey, String.valueOf(objectHash), SET_PARAMS);
                if(TRY_LOCK_SUCCESS.equals(result)) {
                    System.out.println("加锁成功");
                    return true;
                }
                long workTime = System.currentTimeMillis();
                if(workTime - start > timeout) {
                    System.out.println("超时，加锁失败");
                    return false;
                }
                try {
                    // 一秒钟试5次
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("加锁失败，线程中断异常");
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("加锁失败，逻辑运行异常：" +e.toString());
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     *
     * @param jedis jedis客户端
     * @param lockObject 解锁对象
     * @return true -> 解锁成功，false ->解锁失败
     */
    public boolean trySimpleLock(Jedis jedis, Object lockObject) {
        return trySimpleLock(jedis, lockObject, DEFAULT_TRY_SIMPLE_LOCK_TIMEOUT);
    }


    public boolean unlockSimple(Jedis jedis, Object lockObject) {

        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then " +
                        "   return redis.call('del',KEYS[1]) " +
                        " else " +
                        " return 0 " +
                        " end ";

        int objectHash = lockObject == null ? -1 : lockObject.hashCode();
        String lockKey = REDIS_LOCK_PREFIX + objectHash;

        try {
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(String.valueOf(objectHash)));
            return UNLOCK_SUCCESS.equals(result.toString());
        } finally {
            jedis.close();
        }

    }

}
