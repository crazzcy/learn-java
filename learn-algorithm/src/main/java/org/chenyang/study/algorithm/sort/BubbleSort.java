package org.chenyang.study.algorithm.sort;

import org.chenyang.study.algorithm.sort.common.Common;

/**
 * 冒泡排序
 * 基本思想是，从第一个数开始，与下一个数进行对比，将比较出来的大的那个，放到后面，再次与后面的进行对比，选出最大的那个,放到最后。后面再以此类推。
 * 时间复杂度是 O(n^2)，程序可以优化，优化后的冒泡排序，最优时间复杂度是O(n)
 * 空间复杂度是 O(1)
 *
 * @author : ChenYang
 * @date : 2019-07-29 00:38
 * @since :
 */
public class BubbleSort implements Sort{

    @Override
    public void sort(int[] arr) {

        for(int i = arr.length - 1; i > 0; i--) {
            for(int j = 0; j < i; j++) {
                if(arr[j] > arr[j + 1]) {
                    Common.swap(arr, j, j + 1);
                }
            }
            System.out.println("外层已经循环了" + (arr.length - i) + "次");
        }
    }

    @Override
    public void improvedSort(int[] arr) {
        for(int i = arr.length - 1; i > 0; i--) {
            // 加上是否已经交换的标识位，如果一次内循环没有做任何交换，则表示数组已经是有序的，可提前终止
            boolean swapFlag = false;
            for(int j = 0; j < i; j++) {
                if(arr[j] > arr[j + 1]) {
                    Common.swap(arr, j, j + 1);
                    swapFlag = true;
                }
            }

            System.out.println("外层已经循环了" + (arr.length - i) + "次");

            if(!swapFlag) {
                break;
            }
        }

    }
}
