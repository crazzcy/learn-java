package org.chenyang.study.algorithm.sort.common;

import java.util.Arrays;

/**
 * @author : ChenYang
 * @date : 2019-07-28 22:30
 * @since : 1.8+
 */
public class Common {
    public static int[] arr = {3333,2,6,4,9,7,8,5,1,3,233,333};

    public static int[] arr2 = {1,2,3,4,5,6,7,8,9};

    public static int[] arr3 = {9,0,8,9,0,1,2,4,6,7,6,3,7,8,3,4,6,8,4,3,3,5,6,8,9,1,4,5,7,0};

    public static int[] arr4 = {421, 240, 115, 532, 305, 430, 124, 3333, 1};


    /**
     * 交换数组第i条和第j条的数据
     * @param arr 数组
     * @param i 下标i
     * @param j 下标j
     */
    public static void swap(int[] arr, int i, int j) {
        if(arr == null || arr.length == 0 || arr.length < i || arr.length < j || i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 打印数组
     * @param arr 数组
     */
    public static void print(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
}
