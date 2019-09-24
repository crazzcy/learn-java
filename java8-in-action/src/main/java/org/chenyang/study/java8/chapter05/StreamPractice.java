package org.chenyang.study.java8.chapter05;

import org.chenyang.study.java8.chapter05.model.Trader;
import org.chenyang.study.java8.chapter05.model.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ## 5.5 实践
 * @author ChenYang
 * @date 2019-04-19 19:33
 **/
public class StreamPractice {

    static Trader raoul = new Trader("Raoul", "Cambridge");
    static Trader mario = new Trader("Mario","Milan");
    static Trader alan = new Trader("Alan","Cambridge");
    static Trader brian = new Trader("Brian","Cambridge");

    static List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


    /**
     * 找出2011年发生的所有交易，并按交易额排序（从低到高）。
     */
    private static void test1() {
        System.out.println(
            Arrays.toString(
                transactions.stream().filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).toArray()
            )
        );
    }

    /**
     * 交易员都在哪些不同的城市工作过？
     */
    private static void test2() {
        // 可以去掉distinct，使用toSet()
        System.out.println(
                Arrays.toString(
                        transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().toArray()
                )
        );
    }

    /**
     * 查找所有来自于剑桥的交易员，并按姓名排序。
     */
    private static void test3() {
        System.out.println(
                Arrays.toString(
                        transactions.stream().map(Transaction::getTrader).filter(t -> "Cambridge".equals(t.getCity())).sorted(Comparator.comparing(Trader::getName)).distinct().toArray()
                )
        );
    }

    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序。
     */
    private static void test4() {
        System.out.println(
                Arrays.toString(
                        transactions.stream().map(Transaction::getTrader).map(Trader::getName).sorted(Comparator.comparing(String::toString)).distinct().toArray()
                )
        );
    }

    /**
     * 有没有交易员是在米兰工作的？
     */
    private static void test5() {
        System.out.println(
                transactions.stream().map(Transaction::getTrader).anyMatch(t -> "Milan".equals(t.getCity()))
        );
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额。
     */
    private static void test6() {
        transactions.stream().filter(t -> "Cambridge".equals(t.getTrader().getCity())).map(Transaction::getValue).forEach(System.out::println);
    }

    /**
     * 所有交易中，最高的交易额是多少？
     */
    private static void test7() {
        transactions.stream().map(Transaction::getValue).reduce(Integer::max).ifPresent(System.out::println);
    }

    /**
     * 找到交易额最小的交易。
     */
    private static void test8() {
        transactions.stream().map(Transaction::getValue).reduce(Integer::min).ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        System.out.println("--------1------------");
        test1();
        System.out.println("--------2------------");
        test2();
        System.out.println("--------3------------");
        test3();
        System.out.println("--------4------------");
        test4();
        System.out.println("--------5------------");
        test5();
        System.out.println("--------6------------");
        test6();
        System.out.println("--------7------------");
        test7();
        System.out.println("--------8------------");
        test8();
    }
}
