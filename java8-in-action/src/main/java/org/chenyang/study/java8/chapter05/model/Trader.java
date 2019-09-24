package org.chenyang.study.java8.chapter05.model;

/**
 * @author ChenYang
 * @date 2019-04-19 19:52
 **/

public class Trader {

    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    @Override
    public String toString(){
        return "Trader:"+ this.name + " in " + this.city;
    }
}
