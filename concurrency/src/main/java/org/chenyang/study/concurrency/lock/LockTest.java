package org.chenyang.study.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ChenYang
 * @date 2019-03-12 17:24
 **/

public class LockTest {


    private static Lock lock = new ReentrantLock();

    private static Lock lock2 = new ReentrantLock();

    private static Lock lock3 = new ReentrantLock();

    private static Lock lock4 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");
        lock.lock();
        // 轮询锁，如果获取不到，就把拥有的锁都释放了，再轮询获取
        lock2.tryLock();
        // 定时锁
        lock3.tryLock(10L, TimeUnit.SECONDS);

        lock4.lockInterruptibly();
        try {
            System.out.println("lock get lock");
        } finally {
            // 如果没有finally释放Lock，那么相当于启动了一个定时炸弹。
            // 当代码离开保护块的时候，不会自动清除锁。
            // 这就是ReentrantLock不能替代synchronized 的原因，因为它更加危险
            lock.unlock();
        }
    }

}
