package org.chenyang.study.java8.chapter05;

import org.chenyang.study.java8.chapter04.Dish;
import org.chenyang.study.java8.chapter04.TestStream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ChenYang
 * @date 2019-04-18 20:36
 **/

public class TestStream2 {


    /**
     * 测验5.1：你将如何利用流来筛选前两个荤菜呢？
     */
    private static void test51() {
        List<Dish> dishes = TestStream.initDishes();
        System.out.println(Arrays.toString(dishes.stream().filter((dish -> !dish.isVegetarian())).limit(2).toArray()));
    }

    /**
     * 映射
     * 拿出每一个Dish的名称的长度
     */
    private static void testMap() {
        List<Dish> dishes = TestStream.initDishes();
        System.out.println(Arrays.toString(dishes.stream().map(Dish::getName).map(String::length).toArray()));
    }

    /**
     * 测试普通流 将字符串里所有的字符列出来，去重
     */
    private static void testMap2() {
        String[] arrayOfWords = {"Goodbye", "World"};
        System.out.println(Arrays.toString(
                Arrays.stream(arrayOfWords).map(word -> word.split("")).map(Arrays::stream).distinct().toArray()
        ));
    }

    /**
     * TODO:
     * 测试普通流版本2 将字符串里所有的字符列出来，去重，会报错，解不开
     */
    private static void testMap3() {
        String[] arrayOfWords = {"Goodbye", "World"};
        Stream[] streams= (Stream[]) Arrays.stream(arrayOfWords).map(word -> word.split("")).map(Arrays::stream).distinct().toArray();
        System.out.println(Arrays.toString(Arrays.stream(streams).toArray()
        ));
    }

    private static void testFlatMap() {
        String[] arrayOfWords = {"Goodbye", "World"};
        System.out.println(Arrays.toString(
                Arrays.stream(arrayOfWords).map(word -> word.split("")).flatMap(Arrays::stream).distinct().toArray()
        ));
    }

    /**
     * 给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？例如，给定[1, 2, 3, 4, 5]，应该返回[1, 4, 9, 16, 25]。
     *
     */
    private static void test52r1() {
        int[] intArr = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(
                Arrays.stream(intArr).map( x -> x*x ).toArray()
        ));
    }

    /**
     * 给定两个数字列表，如何返回所有的数对呢？
     * 例如，给定列表[1, 2, 3]和列表[3, 4]，应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。
     * 为简单起见，你可以用有两个元素的数组来代表数对。
     *
     */
    private static void test52r2() {
        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(3, 4);
        System.out.println(Arrays.toString(
                number1.stream().flatMap(i -> number2.stream().map(j -> "(" + i + "," + j + ")")).toArray()
        ));
    }

    /**
     * 如何扩展前一个例子，只返回总和能被3整除的数对呢？例如(2, 4)和(3, 3)是可以的。
     */
    private static void test52r3() {
        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(3, 4);
        System.out.println(Arrays.toString(
                number1.stream().flatMap(i -> number2.stream().map(j -> "(" + i + "," + j + ")"))
                        .filter(result -> {
                            result = result.replace("(", "").replace(")", "");
                            int o1 = Integer.parseInt(result.split(",")[0]);
                            int o2 = Integer.parseInt(result.split(",")[1]);
                            return (o1 + o2) % 3 == 0;
                        }).toArray()
        ));
    }

    public static void main(String[] args) {
        test51();
        testMap();
        testMap2();
        //testMap3();
        testFlatMap();
        test52r1();
        test52r2();
        test52r3();
    }
}
