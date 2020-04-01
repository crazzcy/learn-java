package org.chenyang.study.concurrency.abc;

/**
 * @author ChenYang
 * @date 2019-02-23 15:36
 **/

public class PrintThreadA implements Runnable {

    private Object[] locks;
    private static int i = 0;

    public PrintThreadA(Object[] locks) {
        this.locks = locks;
    }

    @Override
    public void run() {
        while (i < 10) {
            synchronized (locks[0]) {
                synchronized (locks[1]) {
                    System.out.println("A");
                    i++;
                    // 通知B运行
                    locks[1].notify();
                }
                try {
                    // 释放自己的锁，等待C通知
                    locks[0].wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
