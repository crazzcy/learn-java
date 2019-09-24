package org.chenyang.study.java8.chapter04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 第四章，简单测试使用流
 * @author ChenYang
 * @date 2019-04-18 20:16
 **/

public class TestStream {

    public static List<Dish> initDishes() {
        return Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
    }

    /**
     * 使用流
     */
    public static void useSimpleStream() {
        List<Dish> dishes = initDishes();
        System.out.println(dishes.stream().filter(Dish::isVegetarian).limit(3).count());
        System.out.println(Arrays.toString(dishes.stream().filter(d -> d.getCalories() < 300).map(Dish::getName).toArray()));
    }

    /**
     * 测试两次使用流
     */
    public static void testTwiceUseStream() {
        List<Dish> dishes = initDishes();
        Stream<Dish> stream = dishes.stream();
        stream.forEach(System.out :: println);
        // 一个流只可以使用一次，这句话报错，java.lang.IllegalStateException: stream has already been operated upon or closed
        stream.forEach(System.out :: println);
    }

    public static void main(String[] args) {
        useSimpleStream();
        testTwiceUseStream();
    }
}
