package org.chenyang.study.concurrency.execution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Runnable任务是不带返回值的
 * @author ChenYang
 * @date 2019-02-25 15:46
 **/

public class MyRunnableExecutor {

    public static void main(String[] args) {
        //ExecutorService executionService = Executors.newFixedThreadPool(10);
        ExecutorService executionService = Executors.newCachedThreadPool();
        for(int i=0; i<100; i++) {
            executionService.execute(new TestRunnable());
        }
        executionService.shutdown();
    }

}


class TestRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "被调用了");
    }
}
