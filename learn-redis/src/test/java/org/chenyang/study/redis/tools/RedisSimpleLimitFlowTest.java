package org.chenyang.study.redis.tools;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ChenYang
 * @date : 2020-05-27 1:43 下午
 * @since :
 */
public class RedisSimpleLimitFlowTest {

    RedisSimpleLimitFlow redisSimpleLimitFlow = new RedisSimpleLimitFlow();

    @Test
    public void testIsAllowActionBySimpleLimitFlow() throws InterruptedException {
        JedisPool jedisPool = setupPool();
        Long testUserId = 25056L;
        // 建立10个线程，启动并访问
        List<Thread> threadList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                Jedis jedis = jedisPool.getResource();
                redisSimpleLimitFlow.isAllowActionBySimpleLimitFlow(jedis, testUserId);
                jedis.close();
            });
            threadList.add(t);
        }
        for(Thread t : threadList) {
            t.start();
            // 模拟，2秒钟，来一个请求
            Thread.sleep(2000);
        }
        for(Thread t : threadList) {
            t.join();
        }
        jedisPool.destroy();
        System.out.println("jedis Pool已销毁，限流测试结束");
    }

    /**
     * 建立jedis连接池
     * @return jedisPool
     */
    private static JedisPool setupPool() {
        String ip = "localhost";
        int port = 6379;
        int timeout = 2000;
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(true);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeout);

        System.out.println("redis连接池创建成功");
        return jedisPool;
    }
}
