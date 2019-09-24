package org.chenyang.study.algorithm.sort.check;

import org.chenyang.study.algorithm.sort.*;

import java.util.Arrays;
import java.util.Random;

/**
 * @author : ChenYang
 * @date : 2019-07-29 00:10
 * @since : 1.8+
 */
public class CheckSort {

    public static void main(String[] args) {
        int[] checkArray = new int[10000];
        int[] checkArray2 = new int[checkArray.length];

        Random random = new Random();

        for(int i = 0; i < checkArray.length; i++) {
            checkArray[i] = random.nextInt();
        }

        System.arraycopy(checkArray, 0, checkArray2, 0, checkArray.length);

        // 自己实现的选择排序
        //Sort sort = new SelectionSort();
        //Sort sort = new BubbleSort();
        //Sort sort = new InsertionSort();
        //Sort sort = new ShellSort();
        //Sort sort = new MergeSort();
        //Sort sort = new QuickSort();
        Sort sort = new HeapSort();
        sort.sort(checkArray);

        // JDK自带的数组排序
        Arrays.sort(checkArray2);

        check(checkArray, checkArray2);

    }


    public static void check(int[] array1, int[] array2) {
        if(Arrays.equals(array1, array2)) {
            System.out.println("Same");
        } else {
            System.out.println("Not Same");
        }
    }

}
