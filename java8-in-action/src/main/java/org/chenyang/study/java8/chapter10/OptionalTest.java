package org.chenyang.study.java8.chapter10;

import org.chenyang.study.java8.chapter01.Apple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author ChenYang
 * @date 2019-04-26 15:58
 **/

public class OptionalTest {

    private static void createOptional() {
        // 声明一个空的Optional
        Optional<Apple> optionalApple = Optional.empty();
        // 创建一个非空的Optional
        Optional<Apple> optionalApple2 = Optional.of(new Apple(12345L));
        // 创建一个可接受null的Optional
        Optional<Apple> optionalApple3 = Optional.ofNullable(null);


        // 从map中提取和转换值
        optionalApple.map(Apple::getColor);
    }

    /**
     * 用Optional封装可能是null的值
     */
    private static void useOptional() {
        Map<String, String> map = new HashMap<>();
        // 可能为Null的值，用Optional封装
        Optional<Object> value = Optional.ofNullable(map.get("key"));
    }



    public static void main(String[] args) {

    }
}
