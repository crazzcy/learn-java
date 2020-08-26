package org.chenyang.study.java8.chapter07;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * ## 7.1 平行流
 * 对流并行处理，使用parallel()，顺序处理使用sequential()，可以指定哪些操作使用并行，哪些操作使用顺序进行
 * stream.parallel() .filter(...) .sequential() .map(...) .parallel() .reduce();
 * @author ChenYang
 * @date 2019-04-23 15:33
 **/
public class TestParallel {


    /**
     * 从1~n的和
     * @param n
     * @return
     */
    private static long sequentialSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0, Long::sum);
    }

    /**
     * 并行计算1~n的和
     * @param n
     * @return
     */
    private static long sequentialSumParallel(long n) {
        return Stream.iterate(1L, i-> i+1).parallel().limit(n).reduce(0L, Long::sum);
    }

    /**
     * 用iterator方法产生的数字是包装类型，要做拆箱操作才能做求和操作
     * 自动装箱的性能消耗，非常惊人，1000万的数据量，用sequentialSumParallel，要执行5000ms，而sequentialSumParallel2只需要20ms
     * @param n
     * @return
     */
    private static long sequentialSumParallel2(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }


    /**
     * 测试方法运行性能
     */
    private static void testFunctionRunTime(Function<Long,Long> function1, Function<Long,Long> function2, Long n) {
        long time1 = System.currentTimeMillis();
        long result1 = function1.apply(n);
        long time2 = System.currentTimeMillis();
        long result2 =function2.apply(n);
        long time3 = System.currentTimeMillis();

        System.out.println("function1 run time： " + (time2 - time1) + "ms");
        System.out.println("function2 run time： "+ (time3 - time2) + "ms");
        System.out.println("result1： " +result1);
        System.out.println("result2： " +result2);

    }

    /**
     * 错误的使用并行流
     */
    private static void wrongUseParallel() {
        // 0~1000 相加=500500
        //LongStream.rangeClosed(1, 1000).forEach(TestParallel::add);

        // 这种就是错误的使用了并行流，多条并行执行add方法，而Add方法并不是原子操作，total处于竞态条件，因此会返回错误的结果
        LongStream.rangeClosed(1,1000).parallel().forEach(TestParallel::add);
    }

    private static long total = 0L;

    private static AtomicLong total2 = new AtomicLong(0);

    private static void add(long value) {
        total+=value;
    }

    private static int[] parseResultType(int inputResultType) {
        return IntStream.rangeClosed(0, 3).filter(resultTypeArgs -> (inputResultType & (1 << resultTypeArgs)) > 0).toArray();
    }

    /**
     * 使用原子变量，非阻塞算法，解决竞态问题
     * @param value
     */
    private static void add2(long value) {
        total2.getAndAdd(value);
    }
//
//    public static void main(String[] args) {
//        //System.out.println(sequentialSum(8));
//        //System.out.println(sequentialSumParallel(8));
//        testFunctionRunTime(TestParallel::sequentialSum, TestParallel::sequentialSumParallel2, 200_000_000L);
//        //wrongUseParallel();
//        //System.out.println(total);
//
//        long[] numbers = LongStream.rangeClosed(1, 200_000_000L).toArray();
//        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
//        long start = System.currentTimeMillis();
//        long result = new ForkJoinPool().invoke(task);
//        long end = System.currentTimeMillis();
//        System.out.println("执行时间：" + (end-start));
//        System.out.println("结果：" + result);
//    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(parseResultType(7)));
        System.out.println(1&1);
    }
}
