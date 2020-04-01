package org.chenyang.study.concurrency.interrupt;

/**
 * 中断的正确理解是：他并不会真正的中断一个线程，而只是发出一个中断请求，然后由线程在下一个合适的时候中断自己
 * @author ChenYang
 * @date 2019-02-26 20:02
 **/

public class TestInterrupt implements Runnable {


    private volatile int index;
    private volatile boolean cancel = false;


    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public int getIndex() {
        return index;
    }

    public TestInterrupt(int idx) {
        this.index = idx;
    }

    @Override
    public void run() {
        while(!isCancel()) {
            System.out.println(Thread.currentThread().getName() + " is running" +getIndex());
            if(Thread.currentThread().isInterrupted()) {
                //throw new InterruptedException("收到中断请求");
            }
            index++;
        }
    }


    public static void main(String[] args) {
        TestInterrupt ti =new TestInterrupt(1);
        Thread t1 = new Thread(ti);
        t1.start();
        boolean flag = true;
        while(flag) {
            System.out.println("主线程里面的index="+ ti.getIndex());
            if(ti.getIndex() >= 100) {
                t1.interrupt();
                ti.setCancel(true);
                flag = false;
            }
        }
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
