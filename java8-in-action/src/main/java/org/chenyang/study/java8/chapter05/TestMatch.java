package org.chenyang.study.java8.chapter05;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ## 5.3 查找和匹配
 * @author ChenYang
 * @date 2019-04-19 15:44
 **/

public class TestMatch {

    /**
     * 测试匹配，匹配是短路操作
     */
    private static void testMatch() {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        // 至少匹配一个
        System.out.println(numbers.stream().anyMatch(x -> x>4));
        // 一个都不匹配
        System.out.println(numbers.stream().noneMatch(x -> x>1));
        // 全部都匹配
        System.out.println(numbers.stream().allMatch(x -> x>0));
    }

    /**
     * 测试查找
     */
    private static void testFind() {
        List<Dish> dishes = TestStream.initDishes();
        Optional<Dish> optionalDish = dishes.stream().filter(Dish::isVegetarian).filter(dish -> dish.getCalories() > 1000 ).findAny();
        optionalDish.ifPresent(d -> System.out.println(d.getName()));
        // 查找第一个
        dishes.stream().filter(d -> d.getCalories() < 351 ).findFirst().ifPresent(d -> System.out.println(d.getName()));
    }


    public static void main(String[] args) {
        testMatch();
        testFind();
    }
}
