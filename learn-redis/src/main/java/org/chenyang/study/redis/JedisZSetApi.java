package org.chenyang.study.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

/**
 *
 * redis zset api
 *
 * @author : ChenYang
 * @date : 2020-04-16 11:11 上午
 * @since :
 */
public class JedisZSetApi {
    /**
     * 测试zset(有序集合)类型 api
     * 官方API共21个
     *
     * zadd: 添加一个有序集合
     * zcard: 返回有序集合的成员数量
     * zcount: 返回分值在min和max之间的成员数量
     * zincrby: 对指定key元素下的member的score加上某个数
     * zrange: 根据元素在有序排列中所处的位置，从有序集合中获取多个元素
     * zrangebyscore: 获取有序集合在给定分值范围内的所有元素
     * zrevrangebyscore: 获取有序集合在给定分值范围内【逆序】返回所有元素，【按分值从大到小返回他们】
     * zlexcount: 当排序集中的所有元素都以相同的分数插入时，此命令返回排序集中元素的数量，其key值介于min和之间max。
     * zrangebylex: 当排序集中的所有元素都以相同的分数插入时，此命令将返回排序集中的所有元素，其key值介于min和之间max。
     * zrevrangebylex:当排序集中的所有元素都以相同的分数插入时，此命令将【逆序】返回排序集中的所有元素，其key值介于min和之间max。
     * zremrangebylex: 当排序集中的所有元素以相同分数插入时，为了强制执行词典排序，此命令将删除存储在key由min和max指定的词典范围之间的所有排序集中的元素。
     * zrank: 返回成员member在有序集合中排名
     * zrevrank: 返回有序集合里的member【逆序】排名，按分值【从大到小】
     * zrem: 删除某个元素
     * zremrangebyrank: 移除有序集合里【排名】介于start和stop之间的所有成员
     * zremrangebyscore: 移除有序集合里【分值】介于mint和max之间的所有成员
     * zrevrange: 返回有序集合里给定范围内的成员，并按分值从大到小排列
     * zscan: like scan
     * zscore: 返回成员member的分值
     * zinterstore: 给定的有序集合做【交集】运算
     * zunionstore: 给定的有序集合做【并集】运算
     */
    public static void testJedisZSetApi(Jedis jedis) {

        String key = "zset-key";
        String key2 = "zset-key2";
        String key3 = "zset-key3";

        String key4 = "zset-key4";


        System.out.println("zadd: " + jedis.zadd(key, 188.88, "zset-member1"));
        System.out.println("zadd: " + jedis.zadd(key, 99.99, "zset-member2"));
        System.out.println("zadd: " + jedis.zadd(key, 199.99, "zset-member3"));

        // 返回类型 Set<String>
        System.out.println("zrange: " + jedis.zrange(key, 0 , -1));
        System.out.println("zrangeByScore: " + jedis.zrangeByScore(key, 100 , 201));
        // 分值从大到小排列
        System.out.println("zrevrangeByScore: " + jedis.zrevrangeByScore(key, 200 , 101));
        // 返回一共有多少值
        System.out.println("zcard: " + jedis.zcard(key));
        // 返回180~200之间score的数量
        System.out.println("zcount: " + jedis.zcount(key, 180, 200));
        // 返回【zset-member2】的排名
        System.out.println("zrank: " + jedis.zrank(key, "zet-member2"));
        // 【zet-member2】的逆序排名，从大到小
        System.out.println("zrevrank: " + jedis.zrevrank(key, "zset-member2"));
        // 返回zset-member2的分数
        System.out.println("zscore: " + jedis.zscore(key, "zset-member2"));

        // 交集
        System.out.println("zinterstore: " + jedis.zinterstore(key3, key, key2));
        System.out.println("zinterstore zrange: " + jedis.zrange(key3, 0 , -1));

        // 并集
        System.out.println("zunionstore: " + jedis.zunionstore(key3, key, key2));
        System.out.println("zunionstore zrange: " + jedis.zrange(key3, 0 , -1));


        System.out.println("zincrby: " + jedis.zincrby(key, 10.01, "zset-member2"));
        System.out.println("zset-member2 zscore: " + jedis.zscore(key,  "zset-member2"));

        ScanParams scanParams = new ScanParams().match("*3");
        ScanResult<Tuple> scanResult =  jedis.zscan(key, "0", scanParams);

        for(Tuple t :scanResult.getResult()) {
            System.out.println("scan elements:" + t.getElement() + ", score: " + t.getScore());
        }


        // 删除member= zset-member0的
        System.out.println("zrem: " + jedis.zrem(key, "zset-member0"));
        // 删掉从0~100的
        System.out.println("zremrangeByScore: " + jedis.zremrangeByScore(key, 0, 100));
        System.out.println("zrange: " + jedis.zrange(key, 0 , -1));

        // 删掉第0~1个的
        System.out.println("zremrangeByRank: " + jedis.zremrangeByRank(key, 0, 1));
        System.out.println("zrange: " + jedis.zrange(key, 0 , -1));


        //lex
        System.out.println("zadd: " + jedis.zadd(key4, 666, "a"));
        System.out.println("zadd: " + jedis.zadd(key4, 666, "b"));
        System.out.println("zadd: " + jedis.zadd(key4, 666, "c"));
        System.out.println("zadd: " + jedis.zadd(key4, 666, "d"));

        // 特殊值+或-用于启动和停止具有特殊意义或正无穷和负无穷大的字符串
        System.out.println("zlexcount:" + jedis.zlexcount(key4, "-", "+"));
        // 有效的开始和停止必须以(或开始[，以便指定范围项目是分别独占的还是包含的。
        System.out.println("zlexcount2:" + jedis.zlexcount(key4, "(a", "[c"));
        // 返回a~c之间（不包含a）的member
        System.out.println("zrangeByLex:" + jedis.zrangeByLex(key4, "(a", "[c"));
        // 逆序返回a~之间的member
        System.out.println("zrevrangeByLex:" + jedis.zrevrangeByLex(key4, "[d", "[a"));

        // 删除返回a~c之间的member
        System.out.println("zremrangeByLex:" + jedis.zremrangeByLex(key4, "[a", "[c"));

        // 这里只剩一个d了
        System.out.println("zrange key4:" + jedis.zrange(key4, 0, -1));


    }
}
