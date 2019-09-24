package org.chenyang.study.java8.chapter05;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.Arrays;
import java.util.List;

/**
 * @author ChenYang
 * @date 2019-04-19 17:13
 **/
public class TestReduce {

    /**
     * identity表示初始值，如果没有初始值，返回的是一个Optional<T>
     */
    private static void testReduce() {
        List<Integer> numbers = Arrays.asList(4,5,7,9,1);
        // 打印数组的和
        System.out.println(numbers.stream().reduce(0, (x,y) -> x+y));
        // 打印最大值
        numbers.stream().reduce((x,y) -> x>y?x:y).ifPresent(System.out:: println);
        // 打印最小值
        numbers.stream().reduce((x,y) -> x<y?x:y).ifPresent(System.out:: println);
    }

    /**
     * 怎样用map和reduce方法数一数流中有多少个菜呢？
     *
     */
    private static void test53() {
        List<Dish> dishes = TestStream.initDishes();
        System.out.println(dishes.stream().map(dish -> 1).reduce(0, (x,y) -> x+y));
    }

    public static void main(String[] args) {
        testReduce();
        test53();
    }
}
