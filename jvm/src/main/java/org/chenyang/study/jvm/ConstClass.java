package org.chenyang.study.jvm;

/**
 * @author : ChenYang
 * @date : 2020-09-14 2:50 下午
 * @since :
 */
public class ConstClass {
    static {
        System.out.println("ConstClass Init");
    }
    public static final String HELLO_WORLD  = "hello-world";
}
