package org.chenyang.study.algorithm.sort;

import java.util.Arrays;

/**
 * 基数排序
 *
 * 算法思想是，本质上是一种多关键字排序。 可以从高位到低位，或者从低位到高位，进行排序。
 * 高位优先（Most Significant First），低位优先（Least Significant First）
 * 高位优先本质上是分治思想，是一种递归排序
 *
 * 时间复杂度：O(n*k)
 * 空间复杂度：O(n)
 *
 * @author : ChenYang
 * @date : 2019-08-06 10:56
 * @since :1.8+
 */
public class RadixSort implements Sort{

    @Override
    public void sort(int[] arr) {
        sortLst(arr, getMostSize(arr));
        sortMst(arr, getMostSize(arr));
    }

    /**
     * 获取数组中的最高位，比如个位，返回1，十位，返回2，百位返回3，以此类推
     * @param arr 数组
     * @return 最高位
     */
    private int getMostSize(int[] arr) {
        int mostSize = 0;
        for(int value : arr) {
            if(mostSize < String.valueOf(value).length()) {
                mostSize = String.valueOf(value).length();
            }
        }
        return mostSize;
    }

    /**
     * 高位优先的基数排序
     * 对于一组数据，从高位开始排，高位分段后，再层层递归，排序到最低位，直到结束
     * 1、从最高位开始切割，切割成N块，并按首位排序
     * 2、对N块的子模块进行下一位的切割
     * @param arr 待排序数组
     * @param mostSize 数组数字的最高位
     */
    private void sortMst(int[] arr, int mostSize) {
        System.out.print("高位优先排序：");
        System.out.println(Arrays.toString(sortMstRecursive(arr, mostSize)));
    }

    /**
     * 高位基数（递归）排序方法
     * @param arr 待排序的本轮数组
     * @param mostSize 最高位
     * @return 分治处理后的结果
     */
    private int[] sortMstRecursive(int[] arr, int mostSize) {
        if(mostSize == 0) {
            return arr;
        }
        if(arr.length == 1) {
            return arr;
        }
        // 按高位切割数组
        int[][] splitArrays = splitArrayByMost(arr, mostSize);
        // 返回结果数组
        int[] resultArray = new int[arr.length];
        // 复制位置
        int copyPosition = 0;
        // 遍历切割数组
        for(int i = 0; i < splitArrays.length; i++) {
            int[] arrays = splitArrays[i];
            int[] sortedArray = sortMstRecursive(arrays, mostSize - 1);
            System.arraycopy(sortedArray, 0, resultArray, copyPosition, sortedArray.length);
            copyPosition += sortedArray.length;
        }
        return resultArray;
    }

    /**
     * 高位切割+排序
     * @param arr 待切割数组
     * @param mostSize 切割的依据（位数）
     * @return 切割后的二维数组
     */
    private int[][] splitArrayByMost(int[] arr, int mostSize) {
        // 先统计位分10个桶
        int[] countArray = new int[10];
        // 统计位备份数组
        int[] countBakArray = new int[10];
        // 排好序的一维数组
        int[] tempResultArray = new int[arr.length];
        // 本次的除数
        int divisor = (int) Math.pow(10 ,mostSize - 1);


        for(int i = 0; i < arr.length; i++) {
            int num = arr[i]/divisor % 10;
            countArray[num]++;
        }

        for(int j = 1; j < countArray.length; j++) {
            countArray[j] = countArray[j] + countArray[j - 1];
        }

        // 备份统计的数字
        System.arraycopy(countArray,0, countBakArray, 0, countArray.length);

        // 数组按照高位排序
        for(int k = arr.length - 1; k >= 0; k--) {
            int num = arr[k]/divisor % 10;
            //resultArray
            tempResultArray[countArray[num] - 1] = arr[k];
            countArray[num]--;
        }
        //System.out.println(Arrays.toString(tempResultArray));


        int resultArrayLength = 0;
        for(int x = 0; x < countBakArray.length; x++) {
            int nowPos = countBakArray[x];
            int beforePos = x > 0 ? countBakArray[x - 1] : 0;
            if(nowPos - beforePos > 0) {
                resultArrayLength++;
            }
        }
        //结果的二维数组
        int[][] resultArray = new int[resultArrayLength][];

        int resultIndex = 0;

        for(int y = 0; y < countBakArray.length; y++) {
            int nowPos = countBakArray[y];
            int beforePos = y > 0 ? countBakArray[y - 1] : 0;
            if(nowPos - beforePos > 0) {
                resultArray[resultIndex] = new int[nowPos - beforePos];
                System.arraycopy(tempResultArray, beforePos, resultArray[resultIndex], 0, resultArray[resultIndex].length);
                resultIndex++;
            }
        }
        return resultArray;
    }

    /**
     * 低位优先的基数排序
     * 对于一组数据，从低位开始排，将排完的结果返回，再从逐渐往高位进行排序
     * @param arr 待排序数组
     * @param mostSize 数组数字的最高位
     */
    private void sortLst(int[] arr, int mostSize) {

        int[] newArray = new int[arr.length];

        System.arraycopy(arr, 0, newArray, 0 , arr.length);

        for(int i = 0; i < mostSize; i++) {
            int[] tmpArray = new int[arr.length];
            int[] countArray = new int[10];

            System.arraycopy(newArray, 0, tmpArray, 0 , arr.length);

            // 获取个位为除以1模10，获取十位为除以10模10，获取百位是除以100模10，以此类推
            int divisor = (int) Math.pow(10 ,i);
            for(int j = 0; j < tmpArray.length; j++) {
                int num = (tmpArray[j] / divisor) % 10;
                countArray[num]++;
            }
            for(int k = 1; k < countArray.length; k++) {
                countArray[k] += countArray[k - 1];
            }

            for(int x = tmpArray.length - 1 ; x >= 0; x--) {
                int num = (tmpArray[x] / divisor) % 10;
                newArray[countArray[num] - 1] = tmpArray[x];
                countArray[num]--;
            }
        }
        System.out.print("低位优先排序：");
        System.out.println(Arrays.toString(newArray));
    }
}
