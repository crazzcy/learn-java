package org.chenyang.study.redis.tools;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.Set;
import java.util.UUID;

/**
 * Redis简单限流工具
 * @author : ChenYang
 * @date : 2020-05-27 11:23 上午
 * @since :
 */
public class RedisSimpleLimitFlow {

    /**
     * 默认限流请求量，单位限定时间内只能请求5次
     */
    private static final Long  DEFAULT_LIMIT_MAX_COUNT  = 5L;

    /**
     * 默认限流时间，10秒
     */
    private static final Long DEFAULT_LIMIT_TIME = 10 * 1000L;

    /**
     * 限流的redis key前缀
     */
    private static final String LIMIT_FLOW_KEY_PREFIX = "redis_simple_limit_flow:";


    /**
     * 使用redis zset做简单限流
     * 原理，每次请求来的之后，写入value=uuid，score=currentTime，使用zrangeScore，查出滑动时间窗口的zset size值为多少，与限流limitCount对比
     * 限流的redis key设置为 simple_limit_flow + userid
     *
     * @param jedis jedis客户端
     * @param userId 用户唯一标识
     * @param limitMaxCount 限流请求量
     * @param pLimitTime 限流时间范围（毫秒）
     * @return true->允许执行，false->不允许执行
     */
    public boolean isAllowActionBySimpleLimitFlow(Jedis jedis, Long userId, Long limitMaxCount, Long pLimitTime) {
        System.out.println("input param: userId=" + userId + ",limitMaxCount=" + limitMaxCount + ",pLimitTime=" + pLimitTime);

        long currentTime = System.currentTimeMillis();
        long userKey = userId == null ? -1 : userId;
        long limitMaxCountR = limitMaxCount == null ? DEFAULT_LIMIT_MAX_COUNT: limitMaxCount;
        long limitTimeR = pLimitTime == null ? DEFAULT_LIMIT_TIME: pLimitTime;

        String limitFlowKey = LIMIT_FLOW_KEY_PREFIX + userKey;

        if(jedis.exists(limitFlowKey)) {
            Pipeline pipeline = jedis.pipelined();
            pipeline.multi();
            pipeline.zadd(limitFlowKey,currentTime, UUID.randomUUID().toString());
            Response<Set<String>> requestSet = pipeline.zrangeByScore(limitFlowKey, currentTime - limitTimeR, currentTime);
            // limitTimeR时间内没操作，自动删除这个key，节约内存空间
            pipeline.pexpire(limitFlowKey, limitTimeR);
            pipeline.exec();
            pipeline.close();

            int requestCount = requestSet.get().size();
            System.out.println("当前限流时段操作次数为" + requestCount);
            if(requestCount > limitMaxCountR) {
                System.out.println("已被限流，用户操作太过频繁，每" + limitTimeR + "秒最多访问" + limitMaxCountR + "次");
                return false;
            }
        } else {
            jedis.zadd(limitFlowKey,currentTime, UUID.randomUUID().toString());
        }
        System.out.println("未被限流，继续访问吧~");
        return true;
    }

    /**
     * 使用redis zset做简单限流重载方法
     * @param jedis jedis客户端
     * @param userId 用户唯一标识
     * @return true->允许执行，false->不允许执行
     */
    public boolean isAllowActionBySimpleLimitFlow(Jedis jedis, Long userId) {
        return isAllowActionBySimpleLimitFlow(jedis, userId, DEFAULT_LIMIT_MAX_COUNT, DEFAULT_LIMIT_TIME);
    }
}
