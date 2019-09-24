package org.chenyang.study.java8.chapter08.designPattern.factory.bean;

/**
 * @author ChenYang
 * @date 2019-04-25 19:59
 **/

public abstract class Product {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
