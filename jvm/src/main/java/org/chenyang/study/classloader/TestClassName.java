package org.chenyang.study.classloader;

import java.util.Date;

/**
 * @author : ChenYang
 * @date : 2021-03-31 1:52 下午
 * @since :
 */
public class Test extends Date {

    public static void main(String[] args) {
        new Test().test();
    }

    public void test() {
        // Test
        System.out.println(super.getClass().getName());
        // Test
        System.out.println(this.getClass().getName());
        // Date
        System.out.println(this.getClass().getSuperclass().getName());
    }
}
