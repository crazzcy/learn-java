package org.chenyang.study.algorithm.sort;

import org.chenyang.study.algorithm.sort.common.Common;

/**
 * 选择排序
 * 1、最简单也是最没用的一种排序
 * 2、算法复杂度比较高，而且不稳定
 * 3、核心思想是、先遍历数组，挨个比较，找到最小的那个值的位置，将那个值的位置，与第一个值交换，以此类推
 * 4、时间复杂度为O(n^2)，最好最坏的时间复杂度都一样。
 * 5、空间复杂度为O(1)，没有额外开销
 *
 * @author : ChenYang
 * @date : 2019-07-28 22:30
 * @since :
 */
public class SelectionSort implements Sort {

    @Override
    public void sort(int[] arr) {

        for(int i = 0; i < arr.length - 1; i++) {
            // 最小值的位置
            int minPosition = i;

            for(int j = i + 1; j < arr.length; j++) {
                if(arr[minPosition] > arr[j]) {
                    minPosition = j;
                }
            }
            Common.swap(arr, i, minPosition);
        }
    }
}
