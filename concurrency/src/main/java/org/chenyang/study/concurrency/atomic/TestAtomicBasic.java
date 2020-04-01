package org.chenyang.study.concurrency.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试原子操作，基础数据类型
 * @author ChenYang
 * @date 2020-03-30 15:46
 **/

public class TestAtomicBasic {

    private static volatile int TEST_INT = 20;
    private final static AtomicInteger TEST_ATOMIC_INT = new AtomicInteger(20);
    private final static int THREAD_COUNT = 1000;

    // other apis
    AtomicLong atomicLong = new AtomicLong();
    AtomicBoolean atomicBoolean = new AtomicBoolean();

    public static void main(String[] args) throws InterruptedException {
        //testInt();
        testAtomic();

    }

    private static void testInt() throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        System.out.println("init num:" + TEST_INT);

        for(int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(()-> {
                int result = TEST_INT++;
                System.out.println("Current Thread Name: " + Thread.currentThread().getName() + ",now=" + result);
            });
            threadList.add(t);
        }

        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        System.out.println("result=" + TEST_INT);
    }

    private static void testAtomic() throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        System.out.println("init num:" +TEST_ATOMIC_INT.get());

        for(int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(()-> {
                int result = TEST_ATOMIC_INT.getAndIncrement();
                System.out.println("Current Thread Name: " + Thread.currentThread().getName() + ",now=" + result);
            });
            threadList.add(t);
        }

        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        System.out.println("result=" + TEST_ATOMIC_INT.get());
    }

}
