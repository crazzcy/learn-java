package org.chenyang.study.java8.chapter08.designPattern.chainOfResponsibility;

/**
 *
 */
public interface MyHandler<T> {
    <T> T handlework(T t);
}
