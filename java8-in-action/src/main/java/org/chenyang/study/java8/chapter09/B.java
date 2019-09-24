package org.chenyang.study.java8.chapter09;

/**
 * @author ChenYang
 * @date 2019-04-26 15:21
 **/

public interface B extends A{
    default void invoke() {
        System.out.println("B");
    }
}
