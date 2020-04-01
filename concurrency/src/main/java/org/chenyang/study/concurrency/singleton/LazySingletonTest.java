package org.chenyang.study.concurrency.singleton;

/**
 * double-check
 * @author ChenYang
 * @date 2019-03-19 10:50
 **/

public class LazySingletonTest {

    private static LazySingletonTest lazySingletonTest = null;


    private LazySingletonTest() {
        // todo 1: XXX
        // todo 2: YYY
    }

    /**
     * 这种方式同步，当需要·频繁获取实例的时候，性能开销较大
     * @return
     */
    public synchronized static LazySingletonTest getInstance() {
        if(lazySingletonTest == null) {
            lazySingletonTest = new LazySingletonTest();
        }
        return lazySingletonTest;
    }

    /**
     * double-check
     * 双层检查
     * @return
     */
    public static LazySingletonTest getInstance2() {
        if(lazySingletonTest == null) {
            synchronized (LazySingletonTest.class) {
                if(lazySingletonTest == null) {
                    // 这里可能会存在JVM指令重排序问题
                    // 这行代码可以分解成3步：1，分配对象内存空间，2，初始化对象，3，设置引用的指向地址
                    // JVM可能会将第2,3步重排序，先引用，再初始化对象
                    // 解决的方法是，将lazySingletonTest 变量声明为`volatile`类型
                    lazySingletonTest = new LazySingletonTest();
                }
            }
        }
        return lazySingletonTest;
    }

    /**
     * 内部类实现多线程环境中的单例模式
     * JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
     * 这样当我们第一次调用getInstance的时候，JVM能够帮我们保证instance只被创建一次，并且会保证把赋值给instance的内存初始化完毕
     * @return
     */
    public static LazySingletonTest getInstance3() {
        return LazySingletonTestHolder.instance;
    }

    /**
     * 内部类实现单例模式，JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
     * 这样当我们第一次调用getInstance的时候，JVM能够帮我们保证instance只被创建一次，并且会保证把赋值给instance的内存初始化完毕
     * 依赖JVM惰性加载机制，LazySingletonTestHolder类也只会在调用getInstance3()的时候，才会加载，因此也实现了懒加载
     * 另外相比带volatile的DCL有个优点，除了可以对静态字段实现延迟初始化外，还可以对实例字段实现延迟初始化。
     */
    private static class LazySingletonTestHolder {
        private static LazySingletonTest instance = new LazySingletonTest();
    }
}
