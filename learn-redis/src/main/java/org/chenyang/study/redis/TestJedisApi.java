package org.chenyang.study.redis;

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
        // 测试String Api
        testJedisStringApi(jedis);
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
        jedis.del("name", "int", "float", "bit", "bit2", "bitop");
        // 清空所有的key
        jedis.flushAll();
        System.out.println("清理所有数据完毕");
    }

    /**
     * 测试jedis 字符串类型API
     * @param jedis jedis 客户端
     */
    public static void testJedisStringApi(Jedis jedis) {
        // 字符串初级K-V
        System.out.println("设置name字段为「chenyang」:" + jedis.set("name", "chenyang"));
        System.out.println("当前name字段为: "+ jedis.get("name"));

        //字符串支持 字符串，整数，浮点数

        // 数字高级
        System.out.println("设置int字段为「8」:" + jedis.set("int", "8"));
        System.out.println("设置float字段为「9.88」:" + jedis.set("float", "9.88"));

        // 给数值加1 =9 INCR命令
        System.out.println("int字段数据加上1:" + jedis.incr("int"));
        //给数值加33 =82 INCRBY命令
        System.out.println("int字段加上33后的数据:" + jedis.incrBy("int", 33L));
        // 给数值减1 =81 DECR命令
        System.out.println("int字段减去1后的数据:" + jedis.decr("int"));
        // 给数值减22 =19 DECRBY 命令
        System.out.println("int字段减去22后的数据:" + jedis.decrBy("int",22L));

        System.out.println("float字段数据:" + jedis.get("float"));
        // 9.88+22.22 = 32.10 INCRBYFLOAT命令
        System.out.println("float字段加上22.22后数据:" + jedis.incrByFloat("float",22.22));
        // 9.88-12.22 = 19.88 INCRBYFLOAT命令
        System.out.println("float字段加上-12.22后数据:" + jedis.incrByFloat("float",-12.22));

        //字符串高级
        // APPEND 命令
        jedis.append("name", "91");
        System.out.println("append之后的字符串为:"+ jedis.get("name"));
        // GETRANGE 命令
        System.out.println("getrange第4~10位的字符串:" + jedis.getrange("name", 4, 10));
        // setrange 将offset开始的字符串，替换为指定的字符，并返回字符串长度
        System.out.println("setrange之后的字符串长度:" + jedis.setrange("name", 1, "991"));
        System.out.println("setrange之后的字符串为:" + jedis.get("name"));

        System.out.println("setbit/getbit 实用用法，可用于网站用户日活，打卡，文章、视频是否观看过。参考博客 https://www.cnblogs.com/K-artorias/p/8863286.html");
        // setbit
        System.out.println("设置bit字段第8位为false: " + jedis.setbit("bit", 8L, false));
        System.out.println("设置bit字段第13位为true: " + jedis.setbit("bit", 13L, true));
        // 前面两个setbit返回为false，第三个返回为true。这里的bitmap是原本有值再setbit返回的是true，而无值setbit,返回的是false
        System.out.println("设置bit字段第13位为true: " + jedis.setbit("bit", 13L, false));
        System.out.println("设置bit字段第1位为true: " + jedis.setbit("bit", 1L, true));

        // getbit
        System.out.println("bit字段第8位:" + jedis.getbit("bit", 8L));
        System.out.println("bit字段第13位:" + jedis.getbit("bit", 13L));
        System.out.println("bit字段第1位:" + jedis.getbit("bit", 1L));

        //bitcount 返回二进制里为1的数量
        System.out.println("bit二进制里为1的数量：" + jedis.bitcount("bit"));

        System.out.println("设置bit2字段第1位为true: " + jedis.setbit("bit2", 1L, true));
        System.out.println("设置bit2字段第13位为true: " + jedis.setbit("bit2", 13L, true));
        System.out.println("设置bit2字段第8位为true: " + jedis.setbit("bit2", 8L, true));

        System.out.println("bit2二进制里为1的数量：" + jedis.bitcount("bit2"));

        //jedis.bitop，两个bit字段做AND/OR/XOR运算操作，返回值是保存到destkey字符串的长度
        System.out.println("BITOP做运算返回destkey字符串：" + jedis.bitop(BitOP.OR, "bitop", "bit1", "bit2"));

        System.out.println("bitop字段第1位为: " + jedis.getbit("bitop", 1L));
        System.out.println("bitop字段第13位为: " + jedis.getbit("bitop", 13L));
        System.out.println("bitop字段第8位为: " + jedis.getbit("bitop", 8L));

        System.out.println("bitop二进制里为1的数量：" + jedis.bitcount("bitop"));

        // jedis.bitpos: Return the position of the first bit set to 1 or 0 in a string.
        System.out.println("bitpos key=bit2中第一个为true的位置: " + jedis.bitpos("bit2", true));

        //jedis.bitfield get
        Long getbitResult = jedis.bitfield("bit", "get", "u30", "0").get(0);
        System.out.println("bitfield: get bit=" + getbitResult);

        //jedis.bitfield set
        //System.out.println("bitfield: set bit=" + jedis.bitfield("bit", "set", "u30", "0", "1"));

        //jedis.bitfield incrby
        //System.out.println("bitfield: set bit=" + jedis.bitfield("bit", "set", "u30", "0", "1"));

    }

    /**
     * 测试jedis 列表类型API
     * @param jedis jedis 客户端
     */
    public void testJedisListApi(Jedis jedis) {
        // 列表 K-V
        jedis.del("list");
        // List left push
        jedis.lpush("list", "haha1", "haha2", "haha3");

        System.out.println(jedis.lrange("list", 0, -1));

        System.out.println(jedis.lindex("list", 2));
    }
}
