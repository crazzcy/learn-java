package org.chenyang.study.classloader;

import java.util.Date;

/**
 * @author : ChenYang
 * @date : 2021-03-31 1:52 下午
 * @since :
 */
public class TestClassName extends Date {

    public static void main(String[] args) {
        new TestClassName().test();
    }

    public void test() {
        // Test
        System.out.println(this.getClass().getName());
        // Test。getClass()方法是final方法，子类不能覆盖，最终调用的其实都是Object里的方法
        System.out.println(super.getClass().getName());
        // Date
        System.out.println(this.getClass().getSuperclass().getName());
    }
}
