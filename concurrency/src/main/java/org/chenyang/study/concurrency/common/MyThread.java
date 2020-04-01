package org.chenyang.study.concurrency.common;

/**
 * @author ChenYang
 * @date 2019-03-20 21:43
 **/

public class MyThread implements Runnable {

    private StackTest stackTest;

    MyThread(StackTest st) {
        this.stackTest = st;
    }

    @Override
    public void run() {
        stackTest.testPrint();
    }
}
