package org.chenyang.study.java8.chapter08.designPattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenYang
 * @date 2019-04-25 22:39
 **/

public class RegisterCenter {

    List<Observer> observerList = new ArrayList<>();

    public void registMedia(Observer observer) {
        observerList.add(observer);
    }

    public void publicNews(String news) {
        observerList.forEach((observer) -> observer.notify(news));
    }
}
