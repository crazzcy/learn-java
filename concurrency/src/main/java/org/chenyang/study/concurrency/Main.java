package org.chenyang.study.concurrency;

public class Main {

    /**
     * Java内存模型要求，变量的读写必须是原子操作
     * 对于64位的变量，double和long类型，JVM允许将其分成2个32位的操作。
     * volatile变量不会被缓存到寄存器或其它处理器不可见的地方
     */
    private static volatile long now = System.currentTimeMillis();

    public static void main(String[] args) {

        System.out.println("Hello World!" + now);
    }
}
