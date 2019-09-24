package org.chenyang.study.java8.chapter01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ## 1.3 测试流
 * @since 1.8+
 * @author ChenYang
 * @date 2019-04-16 10:18
 **/

public class TestStream {
    public static void main(String[] args) {
        List<Apple> inventory = new FilterAppleUtil().getAppleList();
        // 顺序处理
        List<Apple> heavyAppleList = inventory.stream().filter((Apple a) -> a.getWeight() > 150).collect(Collectors.toList());
        System.out.println(Arrays.toString(heavyAppleList.toArray()));

        // 并行处理
        List<Apple> greenAppleList = inventory.parallelStream().filter((Apple a) -> "green".equals(a.getColor())).collect(Collectors.toList());
        System.out.println(Arrays.toString(greenAppleList.toArray()));

    }
}
