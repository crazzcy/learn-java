package org.chenyang.study.algorithm.sort;

import org.chenyang.study.algorithm.sort.common.Common;

/**
 * 希尔排序
 * 基本思想是，先取一个间隔数，在一个数组里，以间隔对数组进行分组，比如间隔为1/2的数组长度为Gap，则可以将数组分为1/2Length的组，先在组内进行插入排序
 * 第二轮，将间隔缩小，再进行插入排序，直到将间隔缩小到1，排序完成
 *
 * 改进算法，据说使用Knuth序列进行希尔排序，比二分法的效率更高
 * Knuth序列： h = 1; h = 3h + 1
 *
 * 时间复杂度：O(n^1.3) （平均）
 * 控件复杂度：O(1)
 * 稳定性：不稳
 *
 * @author : ChenYang
 * @date : 2019-07-31 01:00
 * @since :
 */
public class ShellSort implements Sort {

    @Override
    public void sort(int[] arr) {
        for(int gap = arr.length >> 1; gap > 0; gap = gap >> 1) {
            for(int i = 0; i < arr.length; i++) {
                for(int j = i; j > gap - 1; j -= gap) {
                    if(arr[j] < arr[j-gap]) {
                        Common.swap(arr, j, j-gap);
                    } else {
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void improvedSort(int[] arr) {
        int h = 1;
        while(h < arr.length / 3) {
            h = 3 * h + 1;
        }
        for(int gap = arr.length >> 1; gap > 0; gap = (gap - 1) / 3) {
            for(int i = 0; i < arr.length; i++) {
                for(int j = i; j > gap - 1; j -= gap) {
                    if(arr[j] < arr[j-gap]) {
                        Common.swap(arr, j, j-gap);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
