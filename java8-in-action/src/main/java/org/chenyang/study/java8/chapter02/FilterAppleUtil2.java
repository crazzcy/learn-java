package org.chenyang.study.java8.chapter02;

import org.chenyang.study.java8.chapter01.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 通过行为参数化传递代码
 * java8-in-action采用 1~7种方式 进行过滤
 * 前三种过于啰嗦，不写了
 * 第四种采用策略的设计模式，要建立接口的多个实现，过于麻烦，省略
 * 第五种匿名内部类，方法是filter5
 * 第六种采用java8的 Lambda
 * @author ChenYang
 * @date 2019-04-16 15:07
 **/

public class FilterAppleUtil2 {

    public static List<Apple> initAppleList() {

        List<Apple> inventory = new ArrayList<>();

        Apple apple1 = new Apple(2L,"green",222.22);
        Apple apple2 = new Apple(1L,"red",122.22);
        Apple apple3 = new Apple(4L,"red",422.22);
        Apple apple4 = new Apple(3L,"green",322.22);

        inventory.add(apple1);
        inventory.add(apple2);
        inventory.add(apple3);
        inventory.add(apple4);

        return inventory;
    }

    public static List<Apple> filterApple(List<Apple> appleList, ApplePredicate applePredicate) {
        List<Apple> filterApples = new ArrayList<>();
        for(Apple apple : appleList) {
            if(applePredicate.test(apple)) {
                filterApples.add(apple);
            }
        }
        return filterApples;
    }

    public static void print(List list) {
        System.out.println(Arrays.toString(list.toArray()));
    }

    /**
     * 匿名内部类的方式，将行为方式进行传递参数化
     * @param appleList
     */
    public static List<Apple> filter5(List<Apple> appleList) {
        return filterApple(appleList, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
    }

    /**
     * lambda表达式
     * @param appleList
     * @return
     */
    public static List<Apple> filter6(List<Apple> appleList) {
        return filterApple(appleList, (Apple apple) -> "red".equals(apple.getColor()));
    }

    /**
     * 将List抽象化，可以支持各种List调用
     * @param list
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T>List<T> filter7(List<T> list, Predicate<T> predicate) {
        List<T> resultList = new ArrayList<>();
        for(T t : list) {
            if(predicate.test(t)) {
                resultList.add(t);
            }
        }
        return resultList;
    }


    public static void main(String[] args) {
        List<Apple> inventory = initAppleList();
        print(filter5(inventory));
        print(filter6(inventory));
        print(filter7(inventory, (Apple apple) -> "red".equals(apple.getColor())));

    }


}
