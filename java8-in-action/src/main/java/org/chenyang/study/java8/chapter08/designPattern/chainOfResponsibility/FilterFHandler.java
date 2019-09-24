package org.chenyang.study.java8.chapter08.designPattern.chainOfResponsibility;

/**
 * @author ChenYang
 * @date 2019-04-25 20:31
 **/

public class FilterFHandler implements MyHandler{

    @Override
    public Object handlework(Object o) {
        if(o instanceof String) {
            return String.valueOf(o).replace("fuck", "f**k");
        }
        return o;
    }
}
