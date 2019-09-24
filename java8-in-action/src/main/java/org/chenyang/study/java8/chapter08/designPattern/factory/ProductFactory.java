package org.chenyang.study.java8.chapter08.designPattern.factory;

import org.chenyang.study.java8.chapter08.designPattern.factory.bean.Bond;
import org.chenyang.study.java8.chapter08.designPattern.factory.bean.Loan;
import org.chenyang.study.java8.chapter08.designPattern.factory.bean.Product;
import org.chenyang.study.java8.chapter08.designPattern.factory.bean.Stock;

/**
 * @author ChenYang
 * @date 2019-04-25 20:06
 **/

public class ProductFactory {

    public static Product createProduct(String name) {
        switch (name){
            case("loan"): return new Loan(1,"loan1");
            case("bond"): return new Bond(2,"bond2");
            case("stock"): return new Stock(3,"stock3");
            default: throw new RuntimeException("没这个产品");
        }

    }
}
