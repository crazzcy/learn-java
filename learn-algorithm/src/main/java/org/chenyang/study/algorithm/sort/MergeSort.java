package org.chenyang.study.algorithm.sort;

/**
 * 归并排序，Java，Python内部的对象排序使用的就是改进后的归并排序，Java1.8使用的TimSort，归并+二分插入排序
 *
 * 核心思想是对已经有序的两个数组，两边进行轮询对比，进行合并排序，产生新的有序数组。
 * 如何获得有序的数组，则是使用递归，对数组进行切割，获得子数组，直到子数组的长度为1，从长度为1的子数组，进行不断排序
 * 递归回归，再对已排好序的两两数组，进行再次排序，直到结束
 *
 * 时间复杂度是：O(n*logN)
 * 空间复杂度是：O(n)
 *
 * @author : ChenYang
 * @date : 2019-08-02 21:02
 * @since :
 */
public class MergeSort implements Sort {
    @Override
    public void sort(int[] arr) {
        int[][] splitArray = splitArray(arr);
        int[] resultArr = splitArrayRecursive(splitArray[0], splitArray[1]);
        System.arraycopy(resultArr, 0 , arr, 0, resultArr.length);
    }

    /**
     * 递归调用切割数组
     * @param arrLeft 左边数组
     * @param arrRight 右边数组
     */
    private int[] splitArrayRecursive(int[] arrLeft, int[] arrRight) {
        int[] result1;
        int[] result2;
        if(arrLeft.length > 1) {
            int [][] splitArrays = splitArray(arrLeft);
            result1 = splitArrayRecursive(splitArrays[0], splitArrays[1]);
        } else {
            result1 = arrLeft;
        }

        if(arrRight.length > 1) {
            int [][] splitArrays2 = splitArray(arrRight);
            result2 = splitArrayRecursive(splitArrays2[0], splitArrays2[1]);
        } else {
            result2 = arrRight;
        }
        return mergeSort(result1, result2);
    }

    /**
     * 平衡切割数组，1分为2
     * @param arr 数组
     * @return 切分后两个数组
     */
    private int[][] splitArray(int[] arr) {
        int[][] splitResult = new int[2][];
        int length1 = arr.length / 2;
        int length2 = arr.length - length1;
        int[] arrLeft = new int[length1];
        int[] arrRight = new int[length2];

        for(int i = 0; i< arr.length; i++) {
            if(i < length1) {
                arrLeft[i] = arr[i];
            } else {
                arrRight[i - length1] = arr[i];
            }
        }
        splitResult[0] = arrLeft;
        splitResult[1] = arrRight;
        return splitResult;
    }

    /**
     * 两个数组归并排序
     * @param arrLeft 数组1
     * @param arrRight 数组2
     * @return 排序后的1个数组
     */
    private int[] mergeSort(int[] arrLeft, int[] arrRight) {
        int[] resultArray = new int[arrLeft.length + arrRight.length];
        int i = 0;
        int j = 0;
        int index = 0;

        while(index < resultArray.length) {
            boolean leftOverFlag = i== arrLeft.length;
            boolean rightOverFlag = j == arrRight.length;
            if(!leftOverFlag && (rightOverFlag || arrLeft[i] < arrRight[j])) {
                resultArray[index] = arrLeft[i];
                i++;
            } else {
                resultArray[index] = arrRight[j];
                j++;
            }
            index++;
        }

        return resultArray;
    }

    @Override
    public void improvedSort(int[] arr) {

    }

}
