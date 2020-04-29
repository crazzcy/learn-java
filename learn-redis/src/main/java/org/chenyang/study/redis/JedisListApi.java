package org.chenyang.study.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

/**
 * redis list api
 * @author : ChenYang
 * @date : 2020-04-01 10:02 下午
 * @since :
 */
public class JedisListApi {
    /**
     * 测试jedis List(列表)类型API
     *
     * 官网的命令 一共17个
     *
     * lpush/rpush: 给定的值，推入左/右边
     * lrange: 获取列表给定范围内的所有值
     * lindex: 获取列表在给定位置上的单个元素
     * lpop/rpop: 左/右边弹出一个值，并返回弹出的这个值
     * ltrim: 对列表进行修剪，只保留start~end之间的值
     *
     * blpop/brpop: 左/右边弹出一个值，在timeout秒内阻塞，并返回弹出的这个值
     * rpoplpush/brpoplpush: (阻塞)从source-key的最右边弹出一个元素，移到destkey的最左端，并返回该元素
     *
     * linsert: 列表第n个位置插入一个元素
     * llen: 返回存储在列表的长度
     * lset: 将列表第N个位置设置为xx_value
     * lrem: lrem key count value -> 删除key中，前count个匹配value的值，返回删除的量
     * lpushx/rpushx: 给定的值，推入左/右边，与lpush/rpush不同的是，当key不存在的时候，lpush会创建新的key，并推入，而lpushx则不会
     *
     * @param jedis jedis 客户端
     */
    public static void testJedisListApi(Jedis jedis) {
        // 列表 K-V
        // List left pushx --> 当key不存在的时候，不会插入内容
        System.out.println("lpushx when key does not exist:  "+ jedis.lpushx("list", "haha0"));

        // List left push
        System.out.println("lpush: "+ jedis.lpush("list", "haha1", "haha2", "haha3"));
        System.out.println("print list: " + jedis.lrange("list", 0, -1));

        System.out.println("lpushx when key exist: "+ jedis.lpushx("list", "haha00"));
        System.out.println("print list: " + jedis.lrange("list", 0, -1));

        System.out.println("list index 2 element is -> " + jedis.lindex("list", 2));

        System.out.println("rpushx when key does not exist:  "+ jedis.rpushx("rlist", "hoho0"));

        System.out.println("rpush: "+ jedis.rpush("rlist", "hoho1","hoho2","hoho3"));

        System.out.println("rpushx when key does not exist:  "+ jedis.rpushx("rlist", "hoho0"));

        System.out.println("lpush: "+ jedis.lpush("rlist", "hoho7","hoho8","hoho9"));

        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));

        System.out.println("ltrim: "+ jedis.ltrim("rlist", 2, 5));

        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));

        System.out.println("ltrim: "+ jedis.lrem("rlist", -2, "hoho2"));

        System.out.println("linsert before: "+ jedis.linsert("rlist", ListPosition.BEFORE, "hoho7","hohoinsertbefore"));
        System.out.println("linsert after: "+ jedis.linsert("rlist", ListPosition.AFTER, "hoho7","hohoinsertafter"));
        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));

        System.out.println("rlist len: " + jedis.llen("rlist"));

        System.out.println("lset rlist: "+ jedis.lset("rlist", 4, "hoho3 has been set"));
        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));

        System.out.println("lpop rlist: "+ jedis.lpop("rlist"));
        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));

        System.out.println("rpop rlist: "+ jedis.rpop("rlist"));
        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));

        System.out.println("brpoplpush list to rlist: "+ jedis.brpoplpush("list", "rlist", 0));
        System.out.println("print list: " + jedis.lrange("list", 0, -1));
        System.out.println("print rlist: " + jedis.lrange("rlist", 0, -1));
    }
}
