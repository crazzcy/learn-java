package org.chenyang.study.java8.chapter07;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ChenYang
 * @date 2019-04-24 20:32
 **/

public class TestReduce2 {


    private static void testReduce1() {
        Stream<Integer> integerStream = IntStream.rangeClosed(0, 100).boxed();
        Optional<Integer> optionalInteger = integerStream.reduce(Integer::sum);
        optionalInteger.ifPresent(System.out::println);
    }

    private static void testReduce2() {
        Stream<String> stringStream = Stream.of("h","e", "l", "l", "o");
        String ss = stringStream.reduce("", (s1,s2)->s2+s1);
        System.out.println(ss);
    }

    private static void testReduce3() {
        Stream<String> stringStream = Stream.of("h","e", "l", "l", "o","w","o", "r", "l", "d");
        //1h21e21l21l21o21w21o21r21l21d2
        String s = stringStream.reduce("",(s1,s2) -> s1+"1"+s2+"2",(s3,s4) -> s3 + s4 + "combine");
        System.out.println(s);
    }

    public static void main(String[] args) {
        testReduce1();
        testReduce2();
        testReduce3();
    }
}
