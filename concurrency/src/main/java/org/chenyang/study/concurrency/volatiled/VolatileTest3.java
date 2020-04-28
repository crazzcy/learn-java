package org.chenyang.study.concurrency.volatiled;

/**
 * volatile 测试
 * JMM 不会对volatile语义的数据重排序
 * @author ChenYang
 * @date 2020-04-14 17:33
 **/

public class VolatileTest3 {

    private static int x = 0;
    private static volatile int y = 0;


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> printX());
        Thread t2 = new Thread(() -> printY());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("over");

    }

    private static void printX() {
        int a = 1;
        x = a;
        System.out.println(x);

    }

    private static void printY() {
        int b = 2;
        y = b;
        System.out.println(y);
    }
}
