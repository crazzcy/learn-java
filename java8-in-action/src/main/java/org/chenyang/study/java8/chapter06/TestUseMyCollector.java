package org.chenyang.study.java8.chapter06;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.ArrayList;
import java.util.List;

/**
 * ### 6.5.2 使用自定义的收集器，或者不使用收集器
 * @author ChenYang
 * @date 2019-04-22 20:31
 **/

public class TestUseMyCollector {

    /**
     * 使用自定义的collector
     */
    private static void useMyCollector() {
        List<Dish> dishList = TestStream.initDishes();
        // toList() 是一个工厂
        // System.out.println(dishList.stream().collect(toList()));
        System.out.println(dishList.stream().collect(new MyCollector<>()));
    }


    /**
     * 不使用收集器
     */
    private static void dontUseCollector() {
        List<Dish> dishList = TestStream.initDishes();
        // collect -> (供应源，累加器，合并器)
        // TODO： 这里 Lambda表达式 改成 函数式引用，编译会报错，原因未知
        System.out.println(
            dishList.stream().collect(
                    () -> new ArrayList<>(), List::add, List::addAll
            )
        );
    }

    public static void main(String[] args) {
        //useMyCollector();
        dontUseCollector();
    }
}
