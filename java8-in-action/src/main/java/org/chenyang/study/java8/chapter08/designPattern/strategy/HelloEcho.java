package org.chenyang.study.java8.chapter08.designPattern.strategy;

/**
 * @author ChenYang
 * @date 2019-04-26 0:20
 **/

public class HelloEcho implements Echo{
    @Override
    public void echo(String msg) {
        System.out.println("hello");
    }
}
