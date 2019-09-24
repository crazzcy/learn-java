package org.chenyang.study.java8.chapter08.designPattern.chainOfResponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenYang
 * @date 2019-04-25 20:46
 **/

public class ProcessObject {

    List<MyHandler> handlerList = new ArrayList<>();

    public void addHandler(MyHandler handler) {
        handlerList.add(handler);
    }

    public Object process(Object o) {
        for (MyHandler handler : handlerList) {
            o = handler.handlework(o);
        }
        return o;
    }
}
