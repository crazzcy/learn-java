package org.chenyang.study.java8.chapter08.designPattern.factory;

import org.chenyang.study.java8.chapter08.designPattern.factory.bean.Product;
import org.chenyang.study.java8.chapter08.designPattern.factory.bean.Stock;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author ChenYang
 * @date 2019-04-25 20:02
 **/

public class TestFactory {

    private static void normalFactory() {
        Product product = ProductFactory.createProduct("loan");
        System.out.println(product.getId() + product.getName());
    }

    private static void functionalFactory() {
        Supplier<Product> stockSupplier = () -> new Stock(3, "stock333");
        Product p = stockSupplier.get();
        System.out.println(p.getId() + p.getName());

        BiFunction<Integer, String, Product> stockFactory = Stock::new;
        Product p2 = stockFactory.apply(1,"heheda");
        System.out.println(p2.getId() + p2.getName());
    }

    public static void main(String[] args) {
        normalFactory();
        functionalFactory();
    }
}
