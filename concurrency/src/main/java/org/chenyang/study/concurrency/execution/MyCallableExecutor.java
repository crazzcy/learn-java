package org.chenyang.study.concurrency.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable任务是带返回值的
 * @author ChenYang
 * @date 2019-02-25 16:39
 **/

public class MyCallableExecutor {

    private static List<Future<String>> futureList = new ArrayList<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService excutorService = Executors.newCachedThreadPool();
        for(int i=0; i<100; i++) {
            Future<String> future = excutorService.submit(new TestCallable(i));
            futureList.add(future);
        }

        for(Future<String> future : futureList) {
            String message = future.get();
            if(future.isDone()) {
                System.out.println(message);
            }
        }
    }

}


class TestCallable implements Callable<String> {

    private int id;

    public TestCallable(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        System.out.println("cy print:" + this.id);
        return "test call" + this.id;
    }
}