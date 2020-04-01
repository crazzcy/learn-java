package org.chenyang.study.redis;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;

/**
 * @author : ChenYang
 * @date : 2019-09-22 21:30
 * @since : 1.0+
 */
public class TestJedisApi {

    public static void main(String[] args) {
        // 连接服务器
        Jedis jedis = setup();
        // 重置所有字段
        resetAll(jedis);
        // 测试String API
        // JedisStringApi.testJedisStringApi(jedis);

        // 测试List API
        // JedisListApi.testJedisListApi(jedis);

        // 测试Set API
        JedisSetApi.testJedisSetApi(jedis);
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
     * 重置所有的redis库数据
     * @param jedis jedis 客户端
     */
    private static void resetAll(Jedis jedis) {
        System.out.println("即将清理redis的数据，共" + jedis.dbSize() + "条数据");
        // jedis.del("name", "int", "float", "bit", "bit2", "bitop");
        //jedis.del("list", "rlist");
        jedis.del("set", "set2");
        // 清空所有的key
        jedis.flushAll();
        System.out.println("清理所有数据完毕");
        System.out.println("----------------");
    }

}
