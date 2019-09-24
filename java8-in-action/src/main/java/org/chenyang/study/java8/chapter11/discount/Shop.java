package org.chenyang.study.java8.chapter11.discount;

import java.util.Random;

/**
 * @author ChenYang
 * @date 2019-04-28 16:18
 **/

public class Shop {

    private final String shopName;
    private String product;


    public Shop(String shopName) {
        this.shopName = shopName;
    }

    public Shop(String shopName, String product) {
        this.shopName = shopName;
        this.product = product;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProduct() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return product;
    }

    public double getPrice() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble();
    }

}
