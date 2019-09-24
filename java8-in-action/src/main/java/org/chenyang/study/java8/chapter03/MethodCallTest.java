package org.chenyang.study.java8.chapter03;

import org.chenyang.study.java8.chapter01.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ## 3.6 方法引用测试 && ## 3.7 Lambda方法引用实战
 * @author ChenYang
 * @date 2019-04-17 16:06
 **/

public class MethodCallTest {

    public static void main(String[] args) {
        List<String> strList = Arrays.asList("aa","b","A","B");
        // Lambda表达式方式，倒序排列
        strList.sort((str1, str2) -> str2.compareTo(str1));
        System.out.println("倒序：" + Arrays.toString(strList.toArray()));

        // 方法调用的方式，正序排列
        strList.sort(String:: compareTo);
        System.out.println("正序：" + Arrays.toString(strList.toArray()));

        // 方法调用的方式，倒序排列
        Comparator<String> comparator = Comparator.reverseOrder();
        strList.sort(comparator);
        System.out.println("倒序：" + Arrays.toString(strList.toArray()));

        // 方法调用的方式
        strList.sort(Comparator.comparing(String::length).reversed());
        System.out.println("反序字符长度：" + Arrays.toString(strList.toArray()));

        //构造函数的引用
        Supplier<MethodCallTest> supplier = MethodCallTest::new;
        MethodCallTest test = supplier.get();

        // 一个参数的构造函数
        Function<Long, Apple> function = Apple :: new;
        Apple apple = function.apply(1000L);
        System.out.println(apple);

        // 两个参数的构造函数
        BiFunction<Long, String, Apple> biFunction = Apple::new;
        Apple apple1 = biFunction.apply(1001L, "green");
        System.out.println(apple1);

        // 三个参数的构造函数，JDK里面没有提供，要自己创建一个
        TriFunction<Long, String, Double, Apple> triFunction = Apple::new;
        Apple apple2 = triFunction.apply(1002L, "green", 10.22);
        System.out.println(apple2);
    }
}
