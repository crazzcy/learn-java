package org.chenyang.study.java8.chapter05;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ## 5.6 数值流
 * @author ChenYang
 * @date 2019-04-19 21:45
 **/
public class TestNumStream {
    /**
     * 数值流
     */
    private static void testNumStream() {
        List<Dish> dishes = TestStream.initDishes();
        // 映射成数值流
        System.out.println(dishes.stream().mapToInt(Dish::getCalories).sum());

        //转换成对象流
        dishes.stream().mapToInt(Dish::getCalories).boxed().collect(Collectors.toList()).forEach(System.out::print);

        //默认值OptionalInt，sum返回Int，max返回是OptionalInt
        System.out.println();
        System.out.println(dishes.stream().mapToInt(Dish::getCalories).max().orElse(1));
    }

    /**
     * 数值范围， 1~100之间偶数的个数
     */
    private static void testNumRange() {
        IntStream intStream = IntStream.rangeClosed(1,100).filter( i -> i%2 ==0);
        System.out.println(intStream.count());
    }


    /**
     * 生成给定范围的勾股数
     */
    private static void testGGNum() {
        IntStream.rangeClosed(1,100).boxed().flatMap(a ->
                IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[] {a, b ,Math.sqrt(a*a + b*b)}).filter(t -> t[2] %1 ==0))
                .forEach((result) -> System.out.println("(" +result[0] + "," +result[1] + "," +result[2] + ")"));
    }

    public static void main(String[] args) {
        testNumStream();
        testNumRange();
        System.out.println("----勾股数----------");
        testGGNum();
    }
}
