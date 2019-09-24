package org.chenyang.study.java8.chapter09;

/**
 * @author ChenYang
 * @date 2019-04-26 15:20
 **/

public interface A {
    default void invoke() {
        System.out.println("A");
    }
}
