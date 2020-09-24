package org.chenyang.study.jvm;

/**
 * @author : ChenYang
 * @date : 2020-09-14 2:39 下午
 * @since :
 */
public class ClassInitialization {
    static {
        System.out.println("ClassInitialization Init");
    }

    public static void main(String[] args) {
        notInitialization3();
    }

    /**
     * 这里不会输出SubClass Init，对于静态字段，直接定义了这个字段的类才会被初始化，子类引用父类的变量，只会触发父类加载，不会触发子类加载
     */
    private static void notInitialization1() {
        System.out.println(SubClass.value);
    }

    /**
     * 这里不会输出SuperClass Init，创建动作由字节码指令newarray触发。
     */
    private static void notInitialization2() {
        SuperClass[] superClasses = new SuperClass[10];
    }

    /**
     * 这里不会输出Const Class Init，
     * 因为虽然在Java源码中确实引用了ConstClass类的常量HELLO_WORLD，但其实在编译阶段通过常量传播优化，
     * 已经将此常量的值“hello world”直接存储在ClassInitialization类的常量池中
     * 以后ClassInitialization对常量ConstClass.HELLO_WORLD的引用，实际都被转化为ClassInitialization类对自身常量池的引用了。
     */
    private static void notInitialization3() {
        System.out.println(ConstClass.HELLO_WORLD);
    }

}
