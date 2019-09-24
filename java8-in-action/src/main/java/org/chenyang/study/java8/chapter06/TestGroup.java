package org.chenyang.study.java8.chapter06;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * ## 6.3/ 6.4 分区，分组
 * @author ChenYang
 * @date 2019-04-22 14:40
 **/
public class TestGroup {


    /**
     * 原生方法分组
     */
    private static void testGroup() {
        List<Dish> dishList = TestStream.initDishes();
        System.out.println(dishList.stream().collect(groupingBy(Dish::getType)));
    }

    /**
     * 自定义方法分组
     */
    private static void testGroup2() {
        List<Dish> dishes = TestStream.initDishes();
        System.out.println(dishes.stream().collect(
                groupingBy(
                        d -> {
                            if (d.getCalories() > 700) {
                                return "高热量";
                            } else if (d.getCalories() < 700 && d.getCalories() > 500) {
                                return "普通热量";
                            } else {
                                return "低热量";
                            }
                        }
                )));
    }


    /**
     * 多级分组
     */
    private static void testMultiGroup() {
        List<Dish> dishes = TestStream.initDishes();
        System.out.println(dishes.stream().collect(groupingBy(Dish::getType, groupingBy(
                d -> {
                    if (d.getCalories() > 700) {
                        return "高热量";
                    } else if (d.getCalories() < 700 && d.getCalories() > 500) {
                        return "普通热量";
                    } else {
                        return "低热量";
                    }
                }
        ))));
    }

    /**
     * 多级分组2，统计每组的数量
     */
    private static void testMultiGroup2() {
        List<Dish> dishes = TestStream.initDishes();
        System.out.println(dishes.stream().collect(groupingBy(Dish::getType, counting())));
    }

    /**
     * 按子组收集数据
     */
    private static void testCollectSubGroup() {
        List<Dish> dishes = TestStream.initDishes();
        // 没有用collectingAndThen
        System.out.println(dishes.stream().collect(groupingBy(Dish::getType, maxBy(Comparator.comparing(Dish::getCalories)))));
        // 使用collectingAndThen
        System.out.println(dishes.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)), Optional::get))));

        // summingInt
        System.out.println(dishes.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories))));

        //mapping
        System.out.println(dishes.stream().collect(
                groupingBy(Dish::getType, mapping(d -> {
            if (d.getCalories() > 700) {
                return "高热量";
            } else if (d.getCalories() < 700 && d.getCalories() > 500) {
                return "普通热量";
            } else {
                return "低热量";
            }
        }, toSet()))));

        //mapping2
        System.out.println(dishes.stream().collect(
                groupingBy(Dish::getType, mapping(d -> {
                    if (d.getCalories() > 700) {
                        return "高热量";
                    } else if (d.getCalories() < 700 && d.getCalories() > 500) {
                        return "普通热量";
                    } else {
                        return "低热量";
                    }
                }, toCollection(HashSet::new)))));
    }


    /**
     * 测试分区
     * 分区只是分组的一种特殊情况，只是是由一个谓词作为分区函数
     */
    private static void testPartition() {
        List<Dish> dishes = TestStream.initDishes();
        Map<Boolean, List<Dish>> partitionMenu = dishes.stream().collect(partitioningBy(Dish::isVegetarian));

        List<Dish> vegetableMenu = partitionMenu.get(true);
        System.out.println(Arrays.toString(vegetableMenu.toArray()));

        // 分区同在流中加上Filter, 好处是，保留了是或者不是两套流元素的列表
        dishes.stream().filter(Dish::isVegetarian).collect(Collectors.toList());

        System.out.println(
        dishes.stream().collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)), Optional::get))));
    }

    /**
     * 判断一个数字是不是质数
     * @return
     */
    private static boolean isPrime(int n) {
        // 最慢的解法
        //return IntStream.rangeClosed(2, n-1).noneMatch(i -> n % i == 0);
        // 除以小于平方根的即可
        int s = (int) Math.sqrt(n);
        return IntStream.rangeClosed(2, s).noneMatch( i -> n % i == 0);
    }

    /**
     * 测试一组数字，按照是不是质数来分组
     */
    private static void testPrime() {
        int end = 100;
        System.out.println(
        IntStream.rangeClosed(0, end).boxed().collect(partitioningBy(TestGroup::isPrime))
        );
    }


    public static void main(String[] args) {
        //testGroup();
        //testGroup2();
        //testMultiGroup();
        //testMultiGroup2();
        //testCollectSubGroup();
        //testPartition();
        //System.out.println(isPrime(10));
        testPrime();
    }
}