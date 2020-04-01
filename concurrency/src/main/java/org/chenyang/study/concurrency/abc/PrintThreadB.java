package org.chenyang.study.concurrency.abc;

/**
 * @author ChenYang
 * @date 2019-02-23 15:36
 **/

public class PrintThreadB implements Runnable {

    private Object[] locks;
    private static int i = 0;

    public PrintThreadB(Object[] locks) {
        this.locks = locks;
    }

    @Override
    public void run() {
        while (i < 10) {
            synchronized (locks[1]) {
                synchronized (locks[2]) {
                    System.out.println("B");
                    i++;
                    locks[2].notify();
                }
                try {
                    locks[1].wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
