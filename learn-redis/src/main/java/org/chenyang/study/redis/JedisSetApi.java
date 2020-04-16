package org.chenyang.study.redis;

import redis.clients.jedis.Jedis;

/**
 * redis set api
 * @author : ChenYang
 * @date : 2020-04-01 10:02 下午
 * @since :
 */
public class JedisSetApi {
    /**
     * 测试set(集合)类型 api
     * 官方API共15个
     *
     * sadd: 将给定的元素添加到集合，返回增加的集合数量
     * smembers: 返回集合的所有元素
     * sismember: 检查给定的元素是否存在于集合中
     * srem: 如果给定的元素存在于集合中，则删除。成功返回1，失败返回0
     * spop: 随机从集合中删除一个元素，并返回该元素
     * smove: 从source-key移动一个元素到dest-key，成功返回1，失败返回0
     * scard: 返回集合中的元素数量
     * srandmember: 从集合里返回一个或多个元素，count>0，元素不会重复，否则可能会重复。
     *
     * sdiff: 两个集合差值，返回第一个里面有，第二个里面没有的值
     * sdiffstore: 返回差值，并存储。
     * sinter: 两个集合交集运算。
     * sinterstore: 返回交集，并存储。
     * sunion: 两个集合做并集运算。
     * sunionstore: 返回并集，并存储。
     *
     * sscan: like scan
     *
     * @param jedis redis客户端
     */
    public static void testJedisSetApi(Jedis jedis) {
        System.out.println("jedis sadd: " + jedis.sadd("set", "heheda", "heheda1", "heheda2", "heheda3", "heheda4"));

        System.out.println("jedis smembers: " + jedis.smembers("set"));

        System.out.println("jedis sismember1: " + jedis.sismember("set", "heheda"));

        System.out.println("jedis sismember2: " + jedis.sismember("set", "hehehe"));

        System.out.println("jedis rem1: " + jedis.srem("set", "heheda"));
        System.out.println("jedis rem2: " + jedis.srem("set", "hehehe"));
        System.out.println("jedis smembers: " + jedis.smembers("set"));

        System.out.println("jedis smove heheda2: " + jedis.smove("set", "set2", "heheda2"));
        System.out.println("jedis smembers1: " + jedis.smembers("set"));
        System.out.println("jedis smembers2: " + jedis.smembers("set2"));

        System.out.println("jedis spop: " + jedis.spop("set"));
        System.out.println("jedis smembers1: " + jedis.smembers("set"));

        System.out.println("jedis set scard: " + jedis.scard("set"));
        System.out.println("jedis set2 scard: " + jedis.scard("set2"));

        // count<0 will make repeated data, count>0 will not
        System.out.println("jedis set srandmember: " + jedis.srandmember("set", -3));
        System.out.println("jedis set2 srandmember: " + jedis.srandmember("set2", 3));

        // diff
        System.out.println("sdiff: " + jedis.sdiff("set", "set2"));

        // union
        System.out.println("sunion: " + jedis.sunion("set", "set2"));

        // inter
        System.out.println("sinter: " + jedis.sinter("set", "set2"));

        // union store
        System.out.println("sunion store: " + jedis.sunionstore("set3", "set", "set2"));

        System.out.println("sunion smembers: " + jedis.smembers("set3"));

    }
}
