package org.chenyang.study.concurrency.web;

import java.util.concurrent.Executor;

/**
 * 为每一个任务，启动一个新的线程
 * @author ChenYang
 * @date 2020-03-26 16:35
 **/

public class ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
