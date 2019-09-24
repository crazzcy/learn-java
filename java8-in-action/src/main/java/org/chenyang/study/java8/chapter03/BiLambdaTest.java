package org.chenyang.study.java8.chapter03;

import org.chenyang.study.java8.chapter01.Apple;
import org.chenyang.study.java8.chapter02.FilterAppleUtil2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ## 3.8复合Lambda表达式， ## 3.9 数学中的类似思想
 * @author ChenYang
 * @date 2019-04-17 21:43
 **/

public class BiLambdaTest {
    public static void main(String[] args) {
        // 比较器
        biComparator();
        // 谓词复合
        biPrecadite();
        //函数复合
        biFunction();

        testArea(x -> x+10, 3.0, 7.0);
    }


    /**
     * 比较器复合
     */
    public static void biComparator() {
        List<String> strList = Arrays.asList("aa","bbbb","A","B");
        // 比较器链，先按字符长度，逆序排列，再按字符，逆序排列
        strList.sort(Comparator.comparing(String :: length).reversed().thenComparing(Comparator.reverseOrder()));
        System.out.println(Arrays.toString(strList.toArray()));
        // 比较器链，先按字符长度，逆序排列，再按字符，正序排列
        strList.sort(Comparator.comparing(String :: length).reversed().thenComparing(String::compareTo));
        System.out.println(Arrays.toString(strList.toArray()));
    }

    /**
     * 谓词复合
     */
    public static void biPrecadite() {
        List<Apple> inventory = FilterAppleUtil2.initAppleList();
        Predicate<Apple> redApple = apple -> "red".equals(apple.getColor());
        // red 否定， 全是绿的
        Predicate<Apple> notRedApple = redApple.negate();
        Predicate<Apple> notRedAndHeavy = notRedApple.and(apple -> apple.getWeight() > 300);
        System.out.println(Arrays.toString(FilterAppleUtil2.filter7(inventory, redApple).toArray()));
        System.out.println(Arrays.toString(FilterAppleUtil2.filter7(inventory, notRedApple).toArray()));
        System.out.println(Arrays.toString(FilterAppleUtil2.filter7(inventory, notRedAndHeavy).toArray()));

        // 复杂的谓词，从左向右的优先级
        Predicate<Apple> complicatedPredicate = redApple.or(apple -> 3L == apple.getId()).and(apple -> 4L != apple.getId());
        System.out.println(Arrays.toString(FilterAppleUtil2.filter7(inventory, complicatedPredicate).toArray()));

    }

    /**
     * 函数复合
     */
    public static void biFunction() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        // 4 => g(f(x))
        System.out.println(f.andThen(g).apply(1));
        // 3 => f(g(x))
        System.out.println(f.compose(g).apply(1));

    }

    /**
     * 测试一元函数的计算梯形的面积
     * @param function
     * @param start
     * @param end
     */
    public static void testArea(Function<Double, Double> function, Double start, Double end) {
        double area = (function.apply(start) + function.apply(end)) * (end - start) / 2;
        System.out.println("梯形面积：" + area);
    }

}
