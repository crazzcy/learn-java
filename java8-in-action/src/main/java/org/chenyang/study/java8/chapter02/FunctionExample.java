package org.chenyang.study.java8.chapter02;

import org.chenyang.study.java8.chapter01.Apple;

import java.util.Comparator;
import java.util.List;

/**
 * @author ChenYang
 * @date 2019-04-16 16:07
 **/

public class FunctionExample {

    /**
     * 给苹果排序，匿名内部类
     */
    public static void sortApple() {
        List<Apple> inventory = FilterAppleUtil2.initAppleList();
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        FilterAppleUtil2.print(inventory);
    }

    /**
     * 给苹果排序，Lambda表达式
     */
    public static void sortApple2() {
        List<Apple> inventory = FilterAppleUtil2.initAppleList();
        inventory.sort((Apple a1, Apple a2) -> a2.getId().compareTo(a1.getId()));
        FilterAppleUtil2.print(inventory);
    }

    /**
     * 创建一个线程，匿名内部类
     */
    public static void createNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("I am a new Thread By Anonymous Class");
            }
        });
        t.start();
    }

    public static void createNewThread2() {
        Thread t = new Thread(() -> System.out.println("I am a new Thread by Lambda"));
        t.start();
    }


    public static void main(String[] args) {
        sortApple();
        sortApple2();
        createNewThread();
        createNewThread2();
    }
}
