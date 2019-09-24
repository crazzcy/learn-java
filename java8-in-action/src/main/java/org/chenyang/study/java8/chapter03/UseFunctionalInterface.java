package org.chenyang.study.java8.chapter03;

import org.chenyang.study.java8.chapter02.FilterAppleUtil2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

/**
 * ## 3.4 使用函数式接口
 * 1、Predicate 谓词
 * 2、Consumer 接收一个参数，做一件事，没有返回值
 * 3、Function 接受一个泛型为T的对象，返回R
 * @author ChenYang
 * @date 2019-04-16 20:43
 **/

public class UseFunctionalInterface {

    public static List<String> initStringList() {
        List<String> strList = new ArrayList<>();
        strList.add("asia");
        strList.add("info");
        strList.add("");
        strList.add("chen");
        strList.add("yang");
        strList.add(null);
        return strList;
    }

    private static List<String> filterStringList(List<String> stringList, Predicate<String> predicate) {
        List<String> resultList = new ArrayList<>();
        for(String str : stringList) {
            if(predicate.test(str)) {
                resultList.add(str);
            }
        }
        return resultList;
    }

    /**
     * Consumer接收一个参数，做一件事，没有返回值
     * @param list
     * @param consumer
     * @param <T>
     */
    private static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for(T t : list) {
            consumer.accept(t);
        }
    }

    /**
     * Function 接收一个输入值，返回一个输出值
     * @param list
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    private static <T,R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> resultList = new ArrayList<>();
        for(T t : list) {
            resultList.add(function.apply(t));
        }
        return resultList;
    }

    private static <T>T testSupplier(Supplier<T> supplier) {
        return supplier.get();
    }

    /**
     * my test intBinaryOperator使用
     * 合并两个值
     * @param left
     * @param right
     * @param intBinaryOperator
     * @return
     */
    private static int biTwoValue(int left, int right, IntBinaryOperator intBinaryOperator) {
        return intBinaryOperator.applyAsInt(left, right);
    }

    public static void main(String[] args) {
        // 非空谓词
        Predicate<String> notEmptyPredicate = (String str) -> str != null && !str.isEmpty();
        FilterAppleUtil2.print(filterStringList(initStringList(), notEmptyPredicate));
        forEach(initStringList(), (String str) -> System.out.println(str));
        FilterAppleUtil2.print(map(initStringList(), (String s) -> s == null ? -1: s.length()));
        System.out.println("-------------my test---------------------");
        FilterAppleUtil2.print(testSupplier(() -> initStringList()));
        // 没有给left/right加上类型，有类型推断
        System.out.println(biTwoValue(123, 122, (left, right) -> left - right));
     }
}
