package org.chenyang.study.redis;

import redis.clients.jedis.*;

/**
 * @author : ChenYang
 * @date : 2019-09-22 21:30
 * @since : 1.0+
 */
public class TestJedisApi {

    public static void main(String[] args) {
        // 连接服务器
        // Jedis jedis = setup();
        // 重置所有字段
        // resetAll(jedis);
        // 测试String API
        // JedisStringApi.testJedisStringApi(jedis);

        // 测试List API
        // JedisListApi.testJedisListApi(jedis);

        // 测试Set API
        // JedisSetApi.testJedisSetApi(jedis);

        // 测试Hash API
        // JedisHashApi.testJedisHashApi(jedis);

        // 测试ZSet API
        // JedisZSetApi.testJedisZSetApi(jedis);

        // 测试pub&sub的API
        JedisPool jedisPool = setupPool();

        JedisPubSubApi.testJedisPubSubApi(jedisPool);
    }



    /**
     * 连接redis服务器
     * @return jedis client
     */
    private static Jedis setup() {
        Jedis jedis = new Jedis("localhost", 6379);
        System.out.println("redis连接成功");
        System.out.println("服务正在运行PING:" + jedis.ping());
        return jedis;
    }

    /**
     * 建立jedis连接池
     * @return
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

    /**
     * 重置所有的redis库数据
     * @param jedis jedis 客户端
     */
    private static void resetAll(Jedis jedis) {
        System.out.println("即将清理redis的数据，共" + jedis.dbSize() + "条数据");
        // jedis.del("name", "int", "float", "bit", "bit2", "bitop");
        // jedis.del("list", "rlist");
        // jedis.del("set", "set2");

        // jedis.del("hash", "hash2");
        jedis.del("zset-key", "zset-key2", "zset-key3");

        // 清空所有的key
        jedis.flushAll();
        System.out.println("清理所有数据完毕");
        System.out.println("----------------");
    }

}
