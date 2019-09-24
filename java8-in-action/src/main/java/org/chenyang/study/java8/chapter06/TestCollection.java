package org.chenyang.study.java8.chapter06;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * ## 6.2 规约和汇总
 * @author ChenYang
 * @date 2019-04-21 17:10
 **/

public class TestCollection {

    /**
     * 规约，汇总测试
     */
    private static void testCollector() {
        List<Dish> dishes = TestStream.initDishes();
        long count = dishes.stream().collect(counting());
        System.out.println(count);

        long count2 = dishes.stream().count();
        System.out.println(count2);

        Optional<Dish> optional = dishes.stream().collect(maxBy(Comparator.comparingInt(Dish::getCalories)));
        // 卡路里最大值
        optional.ifPresent(d -> System.out.println("卡路里最大值:" + d.getCalories()));
        // 卡路里总值
        System.out.println("卡路里总值:" + dishes.stream().collect(summingInt(Dish::getCalories)));
        // 卡路里平均值
        System.out.println("卡路里平均值:" + dishes.stream().collect(averagingInt(Dish::getCalories)));
        // 通过一次summarizing操作你可以就数出菜单中元素的个数，并得 到菜肴热量总和、平均值、最大值和最小值：
        System.out.println(dishes.stream().collect(summarizingInt(Dish::getCalories)));
        // 连接字符串
        System.out.println("连接字符串：" + dishes.stream().map(Dish::getName).collect(joining()));
        // 连接字符串2
        System.out.println("连接字符串2：" + dishes.stream().map(Dish::getName).collect(joining(", ")));

    }

    /**
     * 测试规约
     * 测试reducing， 第一个参数是起始值，第二个参数是 参与的域，第三个参数是 规约方法
     * 从广义的规约和汇总来看，其余所有的包括joining/counting/summingInt，都是基于reducing工厂定义的特殊情况而已
     * 封装之后，对程序员而言，更方便调用，而且可读性会更高，这是非常重要的
     */
    public static void testReduce() {
        List<Dish> dishes = TestStream.initDishes();
        // 总和
        int totalCalories = dishes.stream().collect(reducing(0, Dish::getCalories, (x,y) -> x+y ));
        System.out.println("总值："+totalCalories);

        // 最大值，没有设置初始值，返回是Optional
        dishes.stream().collect(reducing((d1,d2) -> d1.getCalories() > d2.getCalories() ? d1:d2)).ifPresent(s -> System.out.print("最大值：" + s.getCalories()));
    }

    public static void main(String[] args) {
        testCollector();
        System.out.println("-------------testReduce-------");
        testReduce();
    }

}
