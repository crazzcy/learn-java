package org.chenyang.study.redis.tools;

import org.chenyang.study.redis.model.Student;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ChenYang
 * @date : 2020-05-27 5:35 下午
 * @since :
 */
public class RedisLockTest {

    RedisLock redisLock = new RedisLock();

    @Test
    public void testRedisSimpleLock() throws InterruptedException {
        JedisPool jedisPool = setupPool();
        Student student = new Student("chenyang", 28, "male");
        int k = 0;
        List<Thread> threadList = new ArrayList<>();
        while(k < 10) {
            k++;
            Thread thread = new Thread(() -> {
                Jedis jedis = jedisPool.getResource();
                try {
                    if(redisLock.trySimpleLock(jedis, student)) {
                        System.out.println("当前线程："+Thread.currentThread().getName() + "获得了锁，并执行业务操作");
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("当前线程："+Thread.currentThread().getName() + "业务操作执行完毕");
                    }
                } finally {
                    if(redisLock.unlockSimple(jedis, student)) {
                        System.out.println("当前线程："+Thread.currentThread().getName() + "释放锁成功");
                    }
                    jedis.close();
                }
            });
            threadList.add(thread);
        }

        for(Thread t : threadList) {
            t.start();
        }
        for(Thread t : threadList) {
            t.join();
        }
        jedisPool.destroy();
        System.out.println("jedis Pool已销毁，简单锁测试结束");


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
