package org.chenyang.study.concurrency.volatiled;

/**
 * volatile 测试类
 * 使用普通的变量 runningFlag，在主线程中将值设置为false，thread-0始终不会停止while循环，也就是说，主线程修改的值，对于thread-0是不可见的
 * Java内存模型规定，所有的变量都保存在主内存中，主内存变量是共享变量。
 * 每个线程拥有自己的工作内存，工作内存拷贝了主内存访问的变量，每个线程操作自己的工作内存，完成后再刷入到主内存
 * 《深入理解java虚拟机》中关于主内存与工作内存的交互协议提到变量在工作内存中改变后必须将该变化同步回主内存
 * 此处的thread-0，读的一直是自己工作内存的旧值，导致出现多线程的可见性问题
 *
 * 解决办法是申明变量为：volatile
 * 当变量被声明成volatile类型后，线程对该变量进行修改后会立即刷新回主内存，而其他线程读取该变量时会先将自己工作内存中的变量置为无效，再从主内存重新读取变量到自己的工作内存，这样就避免发生线程可见性问题。
 *
 * 则：
 * 1.当线程对volatile变量进行写操作时，会将修改后的值刷新回主内存
 * 2.当线程对volatile变量进行读操作时，会先将自己工作内存中的变量置为无效，之后再通过主内存拷贝新值到工作内存中使用。
 * @author ChenYang
 * @date 2019-02-14 20:33
 **/

public class VolatileTest extends Thread {
    private boolean runningFlag = true;
    private int i = 0;

    // private volatile boolean runningFlag = true;

    public boolean isRunningFlag() {
        return runningFlag;
    }

    public void setRunningFlag(boolean runningFlag) {
        this.runningFlag = runningFlag;
    }

    @Override
    public void run() {
        System.out.println(this.getName() + " is Running..." + System.currentTimeMillis());
        while(runningFlag) {
            // ...
            //System.out.println(1);
            i++;
        }
        System.out.println("i=" + i);
        System.out.println("runningFlag修改成功，线程结束" + System.currentTimeMillis());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始跑了");
        VolatileTest volatileTest = new VolatileTest();
        Thread thread = new Thread(volatileTest);
        thread.start();
        Thread.sleep(1000);
        volatileTest.setRunningFlag(false);
    }
}
