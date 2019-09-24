package org.chenyang.study.algorithm.sort;

/**
 * 排序接口
 * @author : ChenYang
 * @date : 2019-07-29 00:44
 * @since :
 */
public interface Sort {
    /**
     * 给数组排序
     * @param arr 数组
     */
    void sort(int[] arr);

    /**
     * 改进的排序
     * @param arr
     */
    default void improvedSort(int[] arr) {

    }
}
