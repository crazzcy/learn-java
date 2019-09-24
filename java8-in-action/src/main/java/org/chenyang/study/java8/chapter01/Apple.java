package org.chenyang.study.java8.chapter01;

/**
 * @author ChenYang
 * @date 2019-04-13 17:24
 **/

public class Apple {
    private Long id;
    private String color;
    private Double weight;


    public Apple() {

    }

    public Apple(Long id) {
        this.id = id;
        this.color = "red";
        this.weight = 0.0;
    }

    public Apple(Long id, String color) {
        this.id = id;
        this.color = color;
        this.weight = 0.0;
    }

    public Apple(Long id, String color, Double weight) {
        this.id = id;
        this.color = color;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "{appleId:" + this.getId() + ",appleColor:" + this.getColor() + ",appleWeight:" + this.getWeight() + "}";
    }

    /**
     * 是不是绿苹果
     * @param apple
     * @return
     */
    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    /**
     * 是不是重苹果
     * @param apple
     * @return
     */
    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

}
