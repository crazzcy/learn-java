package org.chenyang.study.java8.chapter05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * ## 5.7 创建流
 * @author ChenYang
 * @date 2019-04-20 11:14
 **/

public class TestCreateStream {

    /**
     * 值创建
     * 数组创建
     */
    private static void testCreate() {
        Stream<String> stringStream = Stream.of("hello", "world");
        stringStream.map(a -> a.split("")).forEach((a) -> System.out.println(Arrays.toString(a)));

        Stream<Integer> integerStream = Arrays.stream(new int[] { 1,2,3,4,5 }).boxed();
        integerStream.forEach(System.out::println);

    }

    /**
     * 文件创建流
     */
    private static void testFileCreate() {
        // System.out.println(Paths.get("target\\classes\\test.txt").toAbsolutePath());
        try(Stream<String> lines = Files.lines(Paths.get("target\\classes\\test.txt"))) {
            lines.flatMap(line -> Arrays.stream(line.split("")))
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无限流：必须要使用limit操作显式制约他的大小
     * 也不能对无限流进行排序或规约
     * 类似reduce/sorted，需要将流里面的所有数据缓存起来，返回一个新的流，这种称作有状态操作
     * 而map/filter则不需要缓存数据，因此是无状态的操作
     */
    private static void testInfiniteStream() {
        Stream.iterate(0, n -> n+2).limit(10).forEach(System.out::println);

        Stream.generate(Math::random).limit(20).forEach(System.out::println);
    }

    /**
     * 测验5.4：斐波纳契元组序列
     * 斐波纳契元组序列与此类似，是数列中数字和其后续数字组成的元组构成的序列：(0, 1), (1, 1), (1, 2), (2, 3), (3, 5), (5, 8), (8, 13), (13, 21) …
     * 你的任务是用iterate方法生成斐波纳契元组序列中的前20个元素。
     */
    private static void test54() {
        Stream.iterate(new int[] {0, 1},n -> new int[] { n[1], n[0] + n[1] })
                .limit(20)
                .map(n -> "(" + n[0] + "," + n[1] + ")")
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        //testCreate();
        testFileCreate();
        //testInfiniteStream();
        //test54();
    }
}
