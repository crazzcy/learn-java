package org.chenyang.study.java8.chapter08.designPattern.observer;

/**
 * @author ChenYang
 * @date 2019-04-25 22:46
 **/

public class NYTimes implements Observer {

    @Override
    public void notify(String news) {
        System.out.println("From NYTimes breaking news:" + news);
    }
}
