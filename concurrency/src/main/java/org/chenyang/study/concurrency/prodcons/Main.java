package org.chenyang.study.concurrency.prodcons;

/**
 * @author ChenYang
 * @date 2019-02-23 10:12
 **/

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        new Producer("producer-0").cook();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        new Consumer("consumer-0").eat();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread0.start();
        thread1.start();
        thread0.join();
        thread1.join();
    }
}
