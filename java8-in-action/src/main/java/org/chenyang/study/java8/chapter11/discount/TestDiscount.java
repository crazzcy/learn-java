package org.chenyang.study.java8.chapter11.discount;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

/**
 * @author ChenYang
 * @date 2019-05-01 10:57
 **/

public class TestDiscount {

    static List<Shop> shops = Arrays.asList(
            new Shop("shop1", "gumdam:1500:SLIVER"),
            new Shop("shop2", "naruto:2500:GOLD"),
            new Shop("shop3", "onepiece:2800:DIAMOND"),
            new Shop("shop4", "blanch:3300:NONE"));

    /**
     * 同步操作
     * @return
     */
    private static List<String> findPrices() {
        return shops.stream()
                .map(Shop::getProduct)
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    private static List<String> findPriceAsync(ExecutorService executorService) {
        List<CompletableFuture<String>> list =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(shop::getProduct, executorService))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executorService)))
                //.map(future -> future.thenApply(Discount::applyDiscount))
                .collect(toList());

        return list.stream().map(CompletableFuture::join).collect(toList());
    }


    private static void PrintfindPriceTime(ExecutorService executorService) {
        List<CompletableFuture<String>> list =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(shop::getProduct, executorService))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executorService)))
                        //.map(future -> future.thenApply(Discount::applyDiscount))
                        .collect(toList());
        long start = System.currentTimeMillis();
        CompletableFuture[] futures =
                list.stream().map( future ->
                        future.thenAccept((s) ->
                                        System.out.println(s + "已完成，完成时间：" + (System.currentTimeMillis() - start))))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();
        System.out.println("over now" + (System.currentTimeMillis() -start));

    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        System.out.println(Arrays.toString(findPrices().toArray()));
        long time2 = System.currentTimeMillis();
        System.out.println(Arrays.toString(findPriceAsync(Executors.newFixedThreadPool(4)).toArray()));
        long time3 = System.currentTimeMillis();

        System.out.println("同步方法执行时间" + (time2 - time1));
        System.out.println("异步方法执行时间" + (time3 - time2));

        PrintfindPriceTime(Executors.newFixedThreadPool(3));
    }
}
