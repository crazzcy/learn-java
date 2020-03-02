package org.chenyang.study.redis;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author : ChenYang
 * @date : 2020-03-02 11:47 下午
 * @since :
 */

public class MyBloomFilterTest {

    @Test
    public void testMyBloomFilter() {
        long start = System.currentTimeMillis();
        MyBloomFilter myBloomFilter = new MyBloomFilter(1 << 20); // 1048576
        for(int i = 0; i < 10000; i++) {
            myBloomFilter.add(i + "");
        }
        Assert.assertTrue(myBloomFilter.check("1"));
        Assert.assertTrue(myBloomFilter.check("3"));
        Assert.assertTrue(myBloomFilter.check("5"));
        Assert.assertTrue(myBloomFilter.check("9999"));
        Assert.assertTrue(myBloomFilter.check("10002")); // 这里其实是不存在的，当数组小于16时候，非常容易出现误报
        long end = System.currentTimeMillis();

        System.out.println("总的检查耗时大约" + (end - start));

    }

    /*
    @Test
    public void testGuavaBloom() {
        long star = System.currentTimeMillis();
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                10000000,
                0.01);
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(10000000));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
     */
}
