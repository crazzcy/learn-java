package org.chenyang.study.concurrency.abc;

/**
 * @author ChenYang
 * @date 2019-02-23 15:38
 **/

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Object[] locks = new Object[3];
        locks[0] = new Object();
        locks[1] = new Object();
        locks[2] = new Object();

        PrintThreadA pta = new PrintThreadA(locks);
        PrintThreadB ptb = new PrintThreadB(locks);
        PrintThreadC ptc = new PrintThreadC(locks);

        Thread ta = new Thread(pta);
        Thread tb = new Thread(ptb);
        Thread tc = new Thread(ptc);

        ta.start();
        Thread.sleep(100);
        tb.start();
        Thread.sleep(100);
        tc.start();

        ta.join();
        tb.join();
        tc.join();

    }
}
