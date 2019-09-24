package org.chenyang.study.algorithm.sort;

import org.chenyang.study.algorithm.sort.common.Common;

/**
 * 快速排序
 * 核心思想是：
 * 1、对一组数组，先选定一个参考值，（一般是数组第一个，或者最后一个）
 * 2、此刻对该数组同时指定两个指针，一个指针从数组左边，一个指针从数组右边走。
 * 3、左边要找到第一个比该数大的值，右边找到第一个比该参考值小的值，做交换。
 * 4、当左右指针相互碰撞时，遍历结束，再将该参考值，移到碰撞点，此刻比该参考值小的数，都在参考值左边，比参考值大的数，都在其右边，参考值在数组中的位置已确定
 * 5、以参考值交换的位置作为切割点，对数组进行左右切割，再次获得左右的一次准确点，再次递归，直至获取全部
 *
 * 时间复杂度：O(N*logN)
 * 空间复杂度：O(logN)
 *
 * @author : ChenYang
 * @date : 2019-08-03 22:01
 * @since : 1.8+
 */
public class QuickSort implements Sort{

    @Override
    public void sort(int[] arr) {
        // 启动快速排序
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 递归快速排序
     * @param arr 当前数组
     * @param swapLeft 数组左边起始值
     * @param swapRight 数组右边起始值
     */
    private void quickSort(int[] arr, int swapLeft, int swapRight) {

        if(swapLeft >= swapRight) {
            return;
        }
        int position = quickSortSwap(arr, swapLeft, swapRight);

        //System.out.println(Arrays.toString(arr));

        // 递归左边
        quickSort(arr, swapLeft, position - 1);
        // 递归右边
        quickSort(arr, position + 1, swapRight);


    }

    /**
     * 快速排序数据交换，一轮递归下，指定左右两边的分界，进行快速排序的逻辑交换
     * @param arr 待交换的数组
     * @param swapLeft 数组交换左边界
     * @param swapRight 数组交换右边界
     * @return key值的交换点，本次交换确认元素下标位置，即递归下次切割的中间点
     */
    private int quickSortSwap(int[] arr, int swapLeft, int swapRight) {
        int index = swapLeft;
        int key = arr[index];

        while(swapLeft < swapRight) {
            while(swapLeft <= swapRight && arr[swapLeft] <= key) {
                swapLeft++;
            }
            while(swapLeft <= swapRight && arr[swapRight] > key) {
                swapRight--;
            }
            if(swapLeft < swapRight) {
                // 左右交换
                Common.swap(arr, swapLeft, swapRight);
            }
        }
        // key移到右边指针第一个位置上
        Common.swap(arr, swapRight,  index);
        return swapRight;
    }
}
