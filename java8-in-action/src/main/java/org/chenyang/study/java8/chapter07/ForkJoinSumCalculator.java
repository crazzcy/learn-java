package org.chenyang.study.java8.chapter07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * ## 7.2 P151分支求和代码
 * @author ChenYang
 * @date 2019-04-23 17:18
 **/
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    // 最小分解数量
    public static final long THRESHOLD = 10_000;


    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);

    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end-start) < THRESHOLD) {
           return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, (start + end)/2);
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, (start + end)/2, end);
        // 递归
        Long rightTaskResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult+rightTaskResult;
    }

    /**
     * 顺序跑
     * @return
     */
    private long computeSequentially() {
        long sum = 0;
        for(int i=start; i<end; i++) {
            sum += numbers[i];
        }
        return sum;
    }


    public static void main(String[] args) {
        //这个性能看起来比用并行流的版本要差，但这只是因为必须先要把整个数字流都放进一个 long[]，之后才能在ForkJoinSumCalculator任务中使用它。
        long[] numbers = LongStream.rangeClosed(1, 200_000_000L).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        long start = System.currentTimeMillis();
        long result = new ForkJoinPool().invoke(task);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end-start));
        System.out.println("结果：" + result);
    }
}
