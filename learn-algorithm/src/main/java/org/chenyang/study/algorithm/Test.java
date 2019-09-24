package org.chenyang.study.algorithm;

/**
 * @author : ChenYang
 * @date : 2019-08-14 16:22
 * @since :
 */
public class Test {

    public static void main(String[] args) {
        //System.out.println(7 >> 1);
        String key = "1";
        System.out.println(key.hashCode() & 15);
    }
}
