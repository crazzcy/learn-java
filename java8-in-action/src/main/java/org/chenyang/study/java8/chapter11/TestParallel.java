package org.chenyang.study.java8.chapter11;

import org.chenyang.study.java8.chapter11.discount.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author ChenYang
 * @date 2019-04-29 10:52
 **/

public class TestParallel {

    /**
     * 普通流
     * @param shops
     * @return
     */
    private static List<Double> findPrice(List<Shop> shops) {

        long start = System.currentTimeMillis();
        List<Double> prices = shops.stream().map(shop -> shop.getPrice()).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println(Arrays.toString(prices.toArray()));

        System.out.println("findPrice method耗时: "+  (end-start));
        return prices;
    }

    /**
     * 并行流
     * @param shops
     * @return
     */
    private static List<Double> findPrice2(List<Shop> shops) {

        long start = System.currentTimeMillis();
        List<Double> prices = shops.parallelStream().map(shop -> shop.getPrice()).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println(Arrays.toString(prices.toArray()));

        System.out.println("findPrice2 method耗时: "+  (end-start));
        return prices;
    }

    /**
     * 普通流+CompletableFuture
     * @param shops
     * @return
     */
    private static List<Double> findPrice3(List<Shop> shops) {
        long start = System.currentTimeMillis();
        List<CompletableFuture<Double>> prices = shops.stream().map(
                shop ->  CompletableFuture.supplyAsync(shop::getPrice)
        ).collect(Collectors.toList());

        List<Double> result = prices.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("findPrice3 method耗时: "+  (end-start));

        return result;
    }

    public static void main(String[] args) {
        List<Shop> shops = Arrays.asList(new Shop("shop1"),
                                            new Shop("shop2"),
                                            new Shop("shop3"),
                                            new Shop("shop4") );

        findPrice(shops);
        findPrice2(shops);
        findPrice3(shops);
    }
}
