package org.chenyang.study.java8.chapter01;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 过滤苹果工具
 * @since 1.8+
 * @author ChenYang
 * @date 2019-04-13 17:25
 **/
public class FilterAppleUtil {

    private List<Apple> appleList = new ArrayList<>();

    public FilterAppleUtil() {
        for(int i=0; i < 100; i++) {
            Apple apple = new Apple();
            apple.setId((long)i);
            apple.setWeight(i * 1.88);
            apple.setColor(i % 49 == 0 ? "green": "red");
            appleList.add(apple);
        }
    }

    public List<Apple> getAppleList() {
        return appleList;
    }

    /**
     * @param inventory
     * @param predicate 谓词，返回true/false的一个方法
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate) {
        List<Apple> appleList = new ArrayList<Apple>();
        for(Apple apple : inventory) {
            if(predicate.test(apple)) {
                appleList.add(apple);
            }
        }
        return appleList;
    }

    /**
     *
     * @param inventory
     * @param function 传递Function也是可以的。谓词是更标准的方式，效率也更高
     * @return
     */
    public static List<Apple> filterApples2(List<Apple> inventory, Function<Apple, Boolean> function) {
        List<Apple> appleList = new ArrayList<Apple>();
        for(Apple apple : inventory) {
            if(function.apply(apple)) {
                appleList.add(apple);
            }
        }
        return appleList;
    }


    /**
     * 常规过滤法，先遍历，再过滤
     * @return
     */
    public List<Apple> getHeavyApplyList() {
        List<Apple> heavyAppleList = new ArrayList<Apple>();
        List<Apple> appleList = getAppleList();
        for(Apple apple : appleList) {
            if(apple.getWeight() > 150) {
                heavyAppleList.add(apple);
            }
        }
        return heavyAppleList;
    }

    public List<Apple> getHeavyApplyList2() {
        List<Apple> heavyAppleList = getAppleList().stream().filter((Apple a) -> a.getWeight() > 150)
                .collect(Collectors.toList());
        return heavyAppleList;
    }

}
