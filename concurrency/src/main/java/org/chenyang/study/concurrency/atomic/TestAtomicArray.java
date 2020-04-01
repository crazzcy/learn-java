package org.chenyang.study.concurrency.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 测试原子操作，数组类型
 * @author ChenYang
 * @date 2020-03-30 16:39
 **/

public class TestAtomicArray {

    private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);

    public static void main(String[] args) {

        atomicIntegerArray.set(1, 100);

        System.out.println(atomicIntegerArray.get(1));

    }
}
