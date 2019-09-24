package org.chenyang.study.java8.chapter08.designPattern.chainOfResponsibility;

/**
 * @author ChenYang
 * @date 2019-04-25 21:06
 **/

public class AddNameHandler implements MyHandler {

    @Override
    public Object handlework(Object o) {
        if(o instanceof String) {
           return "匿名网友：" + String.valueOf(o);
        }
        return o;
    }
}
