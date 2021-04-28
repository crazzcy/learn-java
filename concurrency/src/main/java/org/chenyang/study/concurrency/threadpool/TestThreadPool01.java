package org.chenyang.study.concurrency.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author : ChenYang
 * @date : 2021-04-27 3:11 下午
 * @since :
 */
public class TestThreadPool {

    private final static int PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        // testExecutor();
        testThreadPoolExecutor();
    }

    public static void testThreadPoolExecutor() {
        int coreSize = 10;
        int maxSize = 20;
        long keepAliveTime = 10L;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadFactory threadFactory = new MyThreadFactory("cy-thread-pool");
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, timeUnit, workQueue, threadFactory, handler);

        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " worker-1 working");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker-1 work out");

        });
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " worker-2 working");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker-2 work out");

        });
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " worker-3 working");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker-3 work out");
        });
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " worker-4 working");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker-4 work out");
        });
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " worker-5 working");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker-5 work out");
        });

        IntStream.range(1, 10).forEach(i -> {
            if(threadPoolExecutor.getActiveCount() == 0) {
                threadPoolExecutor.shutdown();
                System.out.println("executor is shutdown.");
            } else {
                System.out.println("executor is running..");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void testExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(1,Integer.MAX_VALUE).forEach(i -> executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() +" is running");
        }));
        executorService.shutdown();
     }
}

class MyThreadFactory implements ThreadFactory {

    private final String poolNameNamePrefix;
    private final AtomicInteger threadNumber =  new AtomicInteger(1);

    public MyThreadFactory() {
        this.poolNameNamePrefix = "my-pool-default";
    }


    public MyThreadFactory(String poolNameNamePrefix) {
        this.poolNameNamePrefix = poolNameNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r,
                poolNameNamePrefix + "-" + threadNumber.getAndIncrement());
        return t;
    }
}