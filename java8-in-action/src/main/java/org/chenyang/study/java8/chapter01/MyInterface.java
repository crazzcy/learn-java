package org.chenyang.study.java8.chapter01;

/**
 * 测试方法接口
 */
public interface MyInterface {

    /**
     * 接口方法1，实现这个接口，必须override这个方法
     * @return
     */
    String testMethod1();


    /**
     * 接口方法2，加上default关键字，不需要覆盖
     * @return
     */
    default String testMethod2() {
        return "testMethod2";
    }
}
