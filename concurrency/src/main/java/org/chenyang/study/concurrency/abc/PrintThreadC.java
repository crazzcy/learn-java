package org.chenyang.study.concurrency.abc;

/**
 * @author ChenYang
 * @date 2019-02-23 15:36
 **/

public class PrintThreadC implements Runnable {

    private Object[] locks;
    private static int i = 0;

    public PrintThreadC(Object[] locks) {
        this.locks = locks;
    }

    @Override
    public void run() {
        while(i < 10) {
            synchronized (locks[2]) {
                synchronized (locks[0]) {
                    System.out.println("C");
                    i++;
                    locks[0].notify();
                }
                try {
                    locks[2].wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
