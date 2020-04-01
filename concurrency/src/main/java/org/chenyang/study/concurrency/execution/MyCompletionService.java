package org.chenyang.study.concurrency.execution;

import java.util.concurrent.*;

/**
 * @author ChenYang
 * @date 2019-02-25 19:42
 **/

public class MyCompletionService {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);

        for(int i=0 ; i<100; i++) {
            completionService.submit(new TestCallable2(i));
        }
        for(int i=0 ; i<100; i++) {
            Future<String> future = completionService.take();
            String info = future.get();
            System.out.println(info);

        }
    }

}

class TestCallable2 implements Callable<String> {
    private int id;

    public TestCallable2(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        System.out.println("enter call()" + this.id);
        return "call() returned:" + this.id;
    }
}