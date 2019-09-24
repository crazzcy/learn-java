package org.chenyang.study.algorithm.sort;

import org.chenyang.study.algorithm.sort.common.Common;

import java.util.Arrays;

/**
 * 堆排序
 *
 * 核心思想是：
 * 1、首先要建立堆，堆的要求是形成平衡二叉树。需要满足平衡二叉树的条件，并且树的根节点的数字要大于两个子节点。
 * 2、实现堆化算法之后，可以实现小堆的堆化。
 * 3、要考虑从哪里开始堆化，答案是从最后一个节点的父节点开始堆化，并递减至根节点，保证整课树堆化。
 * 4、堆化后，最大值在根节点，将根节点与最后一个子节点交换，此时最后一位已确认。
 * 5、从首位到倒数第二位开始重新堆化，可以获取第二大的数到根节点，此时倒数第二位已确认。
 * 6、循环至第一位.即可完成数组排序。
 *
 * 算法复杂度：O(N*logN)
 * 空间复杂度：O(1)
 *
 * @author : ChenYang
 * @date : 2019-08-16 12:05
 * @since :
 */
public class HeapSort implements Sort {

    /**
     * 堆化，只需满足平衡二叉树，且父大于子即可
     * @param arr 待堆化数组
     * @param lastIndex 指定当前堆化的最后一位
     * @param index 从哪个节点开始堆化
     */
    private void heapify(int[] arr, int lastIndex, int index) {
        if(lastIndex <= index) {
            return;
        }
        // 当前节点的左节点
        int child1 = 2 * index + 1;
        // 当前节点的右节点
        int child2 = 2 * index + 2;

        // 堆里的三个数，寻找最大值
        int max = index;
        if(child1 < lastIndex && arr[max] < arr[child1]) {
            max = child1;
        }
        if(child2 < lastIndex && arr[max] < arr[child2]) {
            max = child2;
        }
        if(max != index) {
            // 表示最大的那个值不是根，则需要交换
            Common.swap(arr, index, max);
            // 交换的位置递归
            heapify(arr, lastIndex, max);
        }

    }

    /**
     * 建立大根堆
     * @param arr 源数组
     */
    private void buildHeap(int[] arr) {
        // 需要考虑的问题是，1、堆化算法，2、从哪里开始堆化(最后一个节点的父节点开始)
        int lastIndex = arr.length - 1;
        // 根据子节点寻找父节点的位置
        int lastParentIndex = (lastIndex - 1) >> 1;
        for(int i = lastParentIndex; i >= 0; i--) {
            heapify(arr, lastIndex, i);
        }
    }

    @Override
    public void sort(int[] arr) {
        // 建立大根堆
        buildHeap(arr);
        for(int i = arr.length - 1; i >= 0; i--) {
            // 交换根节点和最后一个节点，并将最后一个节点截断
            Common.swap(arr, 0, i);
            heapify(arr, i, 0);
        }
        System.out.println(Arrays.toString(arr));
    }
}
