package org.chenyang.study.concurrency.prodcons;

import java.util.List;

/**
 * @author ChenYang
 * @date 2019-02-23 10:09
 **/

public class Producer{

    private String name;
    private volatile List<Toast> toastList = ToastQueue.getInstance();

    /**
     * 吐司序列
     */
    private static Long toastIdSequence;

    public Producer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 更新吐司序列
     * @return 吐司新序列
     */
    public synchronized void updateToastIdSequence() {
        if(toastIdSequence == null) {
            toastIdSequence =  0L;
        } else {
             toastIdSequence++;
        }
    }

    public void cook() throws InterruptedException {
        synchronized (toastList)  {
            while(toastList.size() >= ToastQueue.MAX_LENGTH) {
                System.out.println("生产者"+this.getName()+"进入等待状态");
                toastList.wait();
            }
            updateToastIdSequence();
            Toast toast = new Toast(toastIdSequence, "toast-" + toastIdSequence);
            toastList.add(toast);
            System.out.println("生产者"+this.getName()+"生产了吐司"+ toast.getName() + "放入了队列");
            toastList.notifyAll();
        }
    }
}
