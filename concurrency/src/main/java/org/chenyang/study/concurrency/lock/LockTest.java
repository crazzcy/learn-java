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
        //获取锁。 如果锁不可用，出于线程调度目的，将禁用当前线程，并且在获得锁之前，该线程将一直处于休眠状态。
        lock.lock();
        // 轮询锁，如果获取不到，就把拥有的锁都释放了，再轮询获取
        // 仅在调用时锁为空闲状态才获取该锁。
        // 如果锁可用，则获取锁，并立即返回值 true。如果锁不可用，则此方法将立即返回值 false。
        // 通常对于那些不是必须获取锁的操作可能有用。
        lock2.tryLock();
        // 定时锁
        // 如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁。
        // 如果锁可用，则此方法将立即返回值 true。如果锁不可用，出于线程调度目的，将禁用当前线程，并且在发生以下三种情况之一前，该线程将一直处于休眠状态：(1)锁由当前线程获得；(2)其他某个线程中断当前线程，并且支持对锁获取的中断；(3)已超过指定的等待时间
        // 如果获得了锁，则返回值 true。
        // 如果当前线程：(1)在进入此方法时已经设置了该线程的中断状态；(2)在获取锁时被中断，并且支持对锁获取的中断， 则将抛出 InterruptedException，并会清除当前线程的已中断状态。
        // 如果超过了指定的等待时间，则将返回值 false。如果 time 小于等于 0，该方法将完全不等待。
        lock3.tryLock(10L, TimeUnit.SECONDS);
        // 如果当前线程未被中断，则获取锁。
        // 如果锁可用，则获取锁，并立即返回。
        // 如果锁不可用，出于线程调度目的，将禁用当前线程，并且在发生以下两种情况之一以前，该线程将一直处于休眠状态：(1)锁由当前线程获得；(2)其他某个线程中断当前线程，并且支持对锁获取的中断。
        // 如果当前线程，在进入此方法时已经设置了该线程的中断状态；或者在获取锁时被中断，并且支持对锁获取的中断，则将抛出 InterruptedException，并清除当前线程的已中断状态。
        lock4.lockInterruptibly();
        try {
            System.out.println("lock get lock");
        } finally {
            // 释放锁。对应于lock()、tryLock()、tryLock(xx)、lockInterruptibly()等操作，如果成功的话应该对应着一个unlock()，这样可以避免死锁或者资源浪费。
            // 如果没有finally释放Lock，那么相当于启动了一个定时炸弹。
            // 当代码离开保护块的时候，不会自动清除锁。
            // 这就是ReentrantLock不能替代synchronized 的原因，因为它更加危险
            lock.unlock();
        }
    }

}
