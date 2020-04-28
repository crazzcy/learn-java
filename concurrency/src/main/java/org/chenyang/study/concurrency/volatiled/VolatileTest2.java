package org.chenyang.study.concurrency.volatiled;

/**
 * volatile并不完全具有原子性，对于复合操作其仍存在线程不安全的问题
 * volatile不能保证复合操作具有原子性；解决办法就是给increment方法加锁(lock/synchronized)或将变量声明为原子类类型。
 *
 * Synchronized与volatile区别
 * 1.volatile只能修饰变量，而synchronized可以修改变量，方法以及代码块
 *
 * 2.volatile在多线程中不会存在阻塞问题，synchronized会存在阻塞问题
 *
 * 3.volatile能保证数据的可见性，但不能完全保证数据的原子性，synchronized即保证了数据的可见性也保证了原子性
 *
 * 4.volatile解决的是变量在多个线程之间的可见性，而synchronized解决的是多个线程之间访问资源的同步性
 *
 * @author ChenYang
 * @date 2019-02-14 20:58
 **/

public class VolatileTest2 {

    private volatile int value = 0;

    public void increment() {
        value ++;
        System.out.println(value);
    }

    public static void main(String[] args) {
        VolatileTest2 volatileTest2 = new VolatileTest2();
        for(int i=0; i<1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    volatileTest2.increment();
                }
            }).start();
        }
    }

}
