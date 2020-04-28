package org.chenyang.study.concurrency.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 测试原子操作，数组类型
 * @author ChenYang
 * @date 2020-03-30 16:39
 **/

public class TestAtomicArray {

    private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);

    AtomicIntegerFieldUpdater<Demo> getUpdater(String fieldName) {
        return AtomicIntegerFieldUpdater.newUpdater(Demo.class, fieldName);
    }

    void update() {
        Demo demo = new Demo();
        System.out.println("value1 -> " + getUpdater("value1").decrementAndGet(demo));
        System.out.println("value2 -> " + getUpdater("value2").getAndSet(demo, 10));
        // System.out.println("value3 -> " + getUpdater("value3").incrementAndGet(demo));
        // System.out.println("value4 -> " + getUpdater("value4").compareAndSet(demo, 4,5));
    }

    class Demo {
        public volatile int value1 = 1;
        volatile int value2 = 2;
        protected volatile int value3 = 3; // 外部不可见
        private volatile int value4 = 4; // 外部不可见
    }

    public static void main(String[] args) {
        atomicIntegerArray.set(1, 100);
        System.out.println(atomicIntegerArray.get(1));
        TestAtomicArray testAtomicArray = new TestAtomicArray();
        testAtomicArray.update();
    }

}
