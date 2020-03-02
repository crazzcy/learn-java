package org.chenyang.study.algorithm.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 * @author : ChenYang
 * @date : 2019-10-10 8:18 下午
 * @since :1.8+
 */
public class TestBloomFilter {

    private static final int SIZE = 1000000;

    private static BloomFilter<Integer> bloomFilter =  BloomFilter.create(Funnels.integerFunnel(), SIZE);


    public static void main(String[] args) {
        int i = 0;
        while(i < SIZE) {
            bloomFilter.put(i);
            i++;
        }

        long startTime = System.nanoTime();
        if(bloomFilter.mightContain(29999)) {
            System.out.println("可能命中");
        }
        long endTime = System.nanoTime();
        // 大约是0.2毫秒
        System.out.println("总耗时：" + (endTime - startTime));
    }
}
