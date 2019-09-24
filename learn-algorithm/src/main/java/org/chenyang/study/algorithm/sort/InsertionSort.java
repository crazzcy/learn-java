package org.chenyang.study.algorithm.sort;

import org.chenyang.study.algorithm.sort.common.Common;

/**
 * 插入排序
 * 对基本有序的数组排序效率较快，且稳定
 * 插入排序的核心思想是，先从第二个数开始，与第一个（之前的所有数据）做比较，如果小就交换，这样首先前两个数就是有序的了，后面的数以此类推。
 *
 * 插入排序可以做优化，先将要替换的值，当做一个临时变量，比他大的值，每次往后挪一位，做a[j] = a[j-1]
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * @author : ChenYang
 * @date : 2019-07-29 13:04
 * @since : 1.8+
 */
public class InsertionSort implements Sort {

    @Override
    public void sort(int[] arr) {

        for(int i = 1; i < arr.length; i++) {
            for(int j = i ; j > 0; j--) {
                if (arr[j] < arr[j-1]) {
                    Common.swap(arr, j, j-1);
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void improvedSort(int[] arr) {

        for(int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            for(int j = i; j > 0 ; j--) {
                if(temp < arr[j-1]) {
                    arr[j] =  arr[j-1];
                    if(j == 1) {
                        // 当临时值比第一个值还小，把临时值填到第一个去
                        arr[0] = temp;
                    }
                } else {
                    arr[j] = temp;
                    break;
                }
            }
        }
    }
}
