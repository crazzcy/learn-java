package org.chenyang.study.redis;


/**
 * 布隆过滤器
 * 简单来说，布隆过滤器是一个非常长的单向数组。可以用于判断一个数据是否存在于一个集合内。
 * 比如：写了一个数据`A`到一个集合里，如何判断A是否存在。
 * 在存入数据后，使用不同的hash算法，将A hash出N个不同的结果，分别将这些结果对应的数组的下标，写成1。
 * 查询的时候，将key值分别再hash N次，对应到数组的下标去寻找值，如果不都为1，则肯定不存在。如果都为1，则可能存在。
 * @author : ChenYang
 * @date : 2020-03-01 4:44 下午
 * @since : 1.0
 */
public class MyBloomFilter {

    /**
     * 默认长度 1024
     */
    private static final int DEFAULT_ARRAY_SIZE = 1 << 10;

    /**
     * 布隆过滤器的数组长度，模仿HashMap的数据结构，长度最好是2的指数
     */
    private int arraySize;

    /**
     * 布隆过滤器的bit向量
     */
    private boolean[] array;

    public MyBloomFilter() {
        this.arraySize = DEFAULT_ARRAY_SIZE;
        this.array = new boolean[DEFAULT_ARRAY_SIZE];
    }

    public MyBloomFilter(int arraySize) {
        System.out.println("当前设定的长度" + arraySize);
        this.arraySize = arraySize;
        this.array = new boolean[arraySize];
    }

    /**
     * 增加一个值的时候，布隆过滤器需要修改bit向量
     * @param key key值
     */
    public void add(String key) {
        array[hashCode1(key) & (arraySize - 1)] = true;
        array[hashCode2(key) & (arraySize - 1)] = true;
        array[hashCode3(key) & (arraySize - 1)] = true;
    }

    /**
     * 当前当前key是否存在于当前
     * @param key key值
     * @return true ->存在， false ->不存在
     */
    public boolean check(String key) {
        return array[(hashCode1(key) & (arraySize - 1))]
                && array[(hashCode2(key) & (arraySize - 1))]
                && array[(hashCode3(key) & (arraySize - 1))];
    }

    /**
     * hash算法1
     * @param key key值
     * @return hash值
     */
    private int hashCode1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    /**
     * hash算法2
     * @param key key值
     * @return hash值
     */
    private int hashCode2(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     * hash算法3
     * @param key key值
     * @return hash值
     */
    private int hashCode3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }

}
