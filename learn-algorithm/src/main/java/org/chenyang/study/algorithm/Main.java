package org.chenyang.study.algorithm;

import org.chenyang.study.algorithm.sort.common.Common;
import org.chenyang.study.algorithm.sort.*;

/**
 * @author : ChenYang
 * @date : 2019-07-28 22:51
 * @since :
 */
public class Main {

    public static void main(String[] args) {
        //Sort sort = new SelectionSort();
        //Sort sort = new BubbleSort();
        //Sort sort = new InsertionSort();
        //Sort sort = new ShellSort();
        //Sort sort = new MergeSort();
        //Sort sort = new QuickSort();
        //Sort sort = new CountingSort();
        //Sort sort = new RadixSort();
        Sort sort = new HeapSort();
        sort.sort(Common.arr);
        //sort.sort(Common.arr4);

        //sort.improvedSort(Common.arr3);
        //sort.improvedSort(Common.arr);
        //Common.print(Common.arr);
    }
}
