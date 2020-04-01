package org.chenyang.study.concurrency.common;

import lock.LockTest;

/**
 * @author ChenYang
 * @date 2019-03-20 21:42
 **/

public class StackTest {

    public static void main(String[] args) {
        StackTest st = new StackTest();
        Thread t1 = new Thread(new MyThread(st));
        Thread t2 = new Thread(new MyThread(st));

        t1.start();
        t2.start();
    }

    public void testPrint() {
        LockTest lockTest = new LockTest();
        System.out.println(lockTest);
    }
}
