package org.chenyang.study.java8.chapter01;

/**
 * ## 1.4 测试默认方法
 * @author ChenYang
 * @date 2019-04-16 11:12
 **/
public class TestDefaultMethod implements MyInterface {

    @Override
    public String testMethod1() {
        return null;
    }

    /*
    @Override
    public String testMethod2() {
        return "override testMethod2";
    }
    */

    public static void main(String[] args) {
        MyInterface mi = new TestDefaultMethod();
        System.out.println(mi.testMethod2());
    }
}
