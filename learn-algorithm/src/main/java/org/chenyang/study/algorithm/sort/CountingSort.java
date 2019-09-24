package org.chenyang.study.algorithm.sort;

import java.util.Arrays;

/**
 * 计数排序
 *
 * 基本思想是：先对数组的值做统计，会拥有哪些值，做一个桶，并对这些数值的每个桶初始化计数为0，当遍历数组时，遍历到某个值时，某个值对应的桶位计数加1
 *
 * @author : ChenYang
 * @date : 2019-08-05 17:51
 * @since :
 */
public class CountingSort implements Sort {

    /**
     * 常规计数排序，此刻的区间数字都是重复的
     * 此种排序是不稳定的，没有记录住原数组的位置
     * @param arr 待排序的数组
     */
    @Override
    public void sort(int[] arr) {
        int[] countArray = new int[10];
        int[] newArray = new int[arr.length];
        for(int value : arr) {
            countArray[value]++;
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(countArray));

        int index = 0;
        for(int i = 0; i < countArray.length; i++) {
            while(countArray[i] > 0) {
                newArray[index] = i;
                countArray[i]--;
                index++;
            }
        }
        System.out.println(Arrays.toString(newArray));
    }

    /**
     * 改进的计数排序算法，将其改变成稳定的数组
     *
     * 算法思路是先对桶位做统计增量计数，而不是统计计数。这样获得的数字是每一个桶位在数组中最后一位的下标值。
     *
     * 再向后遍历数组，将每一次遇到的数，放置对应到计数的位置，再将增量计数-1，这样便可以稳定计数排序
     *
     * @param arr 待排序数组
     */
    @Override
    public void improvedSort(int[] arr) {

        int[] countArray = new int[10];
        int[] newArray = new int[arr.length];

        for(int value : arr) {
            countArray[value]++;
        }
        for(int i = 1; i < countArray.length; i++) {
            countArray[i] = countArray[i] + countArray[i - 1];
        }
        for(int i = arr.length - 1; i >= 0; i--) {
            newArray[countArray[arr[i]] - 1] = arr[i];
            countArray[arr[i]]--;
        }
        System.out.println(Arrays.toString(newArray));
    }
}
