package org.chenyang.study.java8.chapter11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author ChenYang
 * @date 2019-04-27 11:02
 **/
public class TestCompletableFuture {

    /**
     * 同步方法，返回一个double随机数
     * @return
     * @throws InterruptedException
     */
    private static double getPrice() throws InterruptedException {
        Thread.sleep(5000);
        return new Random().nextDouble();
    }

    /**
     * 异步方法，显式创建一个Thread异步执行
     * @return
     */
    private static Future<Double> getPriceAsync() {
        CompletableFuture<Double> priceFuture = new CompletableFuture<>();

        new Thread(() -> {
            try {
                System.out.println("远程计算价格中....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double price = new Random().nextDouble();
            priceFuture.complete(price);
        }).start();

        return priceFuture;
    }

    /**
     * 异步方法，使用CompletableFuture.supplyAsync工厂方法创建
     * @return
     */
    private static Future<Double> getPriceAsync2() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("远程计算价格中....");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Random().nextDouble();
        });
    }

    /**
     * 错误处理
     * @return
     */
    private static Future<Double> getPriceAsync3() {
        CompletableFuture<Double> priceFuture = new CompletableFuture<>();

        new Thread(() -> {
            try {
                System.out.println("远程计算价格中....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                priceFuture.completeExceptionally(e);
            }
            double price = new Random().nextDouble();
            priceFuture.complete(price);
        }).start();

        return priceFuture;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(getPrice());
        System.out.println("同步方法，要等到price出来，才继续往这里执行");
        Future<Double> priceAsync = getPriceAsync();
        System.out.println(priceAsync.isDone());
        System.out.println("异步方法，虽然Price没有，这里依然可以执行");
        try {
            double price = priceAsync.get();
            System.out.println(price);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(getPriceAsync2().get());
            System.out.println("全部结束");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
