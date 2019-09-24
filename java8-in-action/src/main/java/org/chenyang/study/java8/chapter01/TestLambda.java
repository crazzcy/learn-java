package org.chenyang.study.java8.chapter01;

import java.util.Arrays;
import java.util.List;

/**
 * ### 1.2.3 测试Lambda函数
 * @since 1.8+
 * @author ChenYang
 * @date 2019-04-15 20:19
 **/

public class TestLambda {

    public static void main(String[] args) {
        FilterAppleUtil tf = new FilterAppleUtil();
        List<Apple> appleList = tf.getAppleList();
        // 将Apple里面的isGreenApple方法， 传递给filterApples方法
        System.out.println(Arrays.toString(FilterAppleUtil.filterApples(appleList, (Apple a) -> "red".equals(a.getColor())).toArray()));
    }
}
