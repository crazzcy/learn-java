package org.chenyang.study.concurrency.prodcons;

import java.util.List;

/**
 * 消费者
 * @author ChenYang
 * @date 2019-02-23 10:09
 **/

public class Consumer {

    private String name;
    private volatile List<Toast> toastList = ToastQueue.getInstance();

    public Consumer(String name) {
       this.name = name;
    }

    public void eat() throws InterruptedException {
        synchronized (toastList) {
            // 这里是使用wait和notify函数的规范代码模板，即使用while而不是使用if
            // 使用while的好处是，当前线程被唤醒的时候，再次检查被唤醒的条件是否满足
            while (toastList.size() == 0) {
                toastList.wait();
            }
            Toast toast = toastList.get(0);
            System.out.println("消费者：" + this.name + "吃了" + toastList.get(0).getName()) ;
            toastList.remove(toast);
            toastList.notifyAll();
        }
    }
}
