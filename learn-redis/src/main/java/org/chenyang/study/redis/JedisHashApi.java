package org.chenyang.study.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * redis hash api
 * @author : ChenYang
 * @date : 2020-04-02 8:21 下午
 * @since :
 */
public class JedisHashApi {

    /**
     * 测试hash(散列)类型 api
     * 官方API共15个
     *
     * hget/hset:  返回hash-key下，某个sub-hash-key的值/ 设置hash值
     * hdel: 删除某个hash-key下的某个sub-hash-key
     * hexists: 某个hash-key 是否存在
     * hsetnx: 如果某个hash-key不存在，则set
     * hgetall: 获取某个hash-key下所有的散列值
     * hincrby/hincrbyfloat: 在当前hash-key的sub-hash-key自增一个数
     * hkeys: 某个hash-key下的所有key
     * hvals: 某个hash-key下的所有value
     * hlen: 某个hash-key的长度，共有多少元素
     * hstrlen: 某个hash-key下sub-hash-key下元素的字符串长度
     *
     * hmget/hmset:
     *
     * hscan: like scan
     *
     * @param jedis redis客户端
     */
    public static void testJedisHashApi(Jedis jedis) {

        System.out.println("hset: " + jedis.hset("hash", "hash-key", "hash-value1"));
        System.out.println("hset: " + jedis.hset("hash", "hash-key", "hash-value2"));
        System.out.println("hset: " + jedis.hset("hash", "hash-key", "hash-value3"));

        System.out.println("hset: " + jedis.hset("hash", "hash-key2", "hash-value4"));

        System.out.println("hset: " + jedis.hset("hash2", "hash-key", "hash-value5"));

        System.out.println("hexists: " + jedis.hexists("hash2", "hash-key"));
        System.out.println("hexists: " + jedis.hexists("hash2", "hash-key2"));
        System.out.println("hexists: " + jedis.hexists("hash", "hash-key2"));


        System.out.println("hget: " + jedis.hget("hash", "hash-key"));
        System.out.println("hgetAll: " + jedis.hgetAll("hash"));
        System.out.println("hdel: " + jedis.hdel("hash2", "hash-key"));
        System.out.println("hlen: " + jedis.hlen("hash"));

        System.out.println("hstrLen: " + jedis.hstrlen("hash", "hash-key"));


        System.out.println("hkeys: " + jedis.hkeys("hash"));
        System.out.println("hvals: " + jedis.hvals("hash"));

        System.out.println("hincrBy: " + jedis.hincrBy("hash", "hash-long", 33L));
        System.out.println("hincrBy: " + jedis.hincrBy("hash", "hash-long", 65L));
        System.out.println("hincrBy -- get: " + jedis.hget("hash", "hash-long"));

        System.out.println("hincrByFloat: " + jedis.hincrByFloat("hash", "hash-float", 66.66));
        System.out.println("hincrByFloat: " + jedis.hincrByFloat("hash", "hash-float", 33.32));

        System.out.println("hincrByFloat -- get: " + jedis.hget("hash", "hash-float"));


        Map<String, String> hmMap = new HashMap<>(16);
        hmMap.put("s1", "uniquo");
        hmMap.put("s2", "h&m");
        hmMap.put("s3", "zara");

        System.out.println("hmset: " + jedis.hmset("cloth-shop", hmMap));

        System.out.println("hmget: " + jedis.hmget("cloth-shop", "s1", "s2", "s3"));
    }
}
