package org.chenyang.study.java8.chapter03;

/**
 * 测试函数式接口
 * @author ChenYang
 * @date 2019-04-16 19:23
 **/

@FunctionalInterface
public interface TestFunctionalInterface {

    void testMethod();

    // 声明了函数式接口，只能有一个函数描述符，否则会编译错误
    //void testMethod2();
}
