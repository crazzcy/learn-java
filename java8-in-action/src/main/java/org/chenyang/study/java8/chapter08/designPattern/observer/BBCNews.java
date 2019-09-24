package org.chenyang.study.java8.chapter08.designPattern.observer;

/**
 * @author ChenYang
 * @date 2019-04-25 22:48
 **/

public class BBCNews implements Observer {
    @Override
    public void notify(String news) {
        System.out.println("Here is a news from BBC:" + news);
    }
}
