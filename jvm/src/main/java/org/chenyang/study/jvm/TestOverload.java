package org.chenyang.study.jvm;

import java.io.Serializable;

/**
 * 需要注意Javac编译器虽然能确定出方法的重载版本，但在很多情况下这个重载版本并不是“唯 一”的，往往只能确定一个“相对更合适的”版本。
 * @author : ChenYang
 * @date : 2021-04-08 5:09 下午
 * @since :
 */
public class TestOverload {

    public static void sayHello(Object arg) { System.out.println("hello Object");}
    // public static void sayHello(int arg) { System.out.println("hello int");}
    // public static void sayHello(long arg) { System.out.println("hello long"); }
    // public static void sayHello(Character arg) { System.out.println("hello Character"); }
    // public static void sayHello(char arg) { System.out.println("hello char");}
    public static void sayHello(char... arg) { System.out.println("hello char ...");}
    public static void sayHello(Serializable arg) { System.out.println("hello Serializable");}

    public static void main(String[] args) {
        sayHello('a');
    }
}
