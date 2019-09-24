package org.chenyang.study.java8.chapter13;

/**
 * chapter13 函数式递归
 * @author ChenYang
 * @date 2019-06-06 12:03
 **/

public class FunctionRecursive {

    /**
     * 递归实现阶乘
     * @param n
     * @return
     */
    private static long recursiveCompute(long n) {
        return n == 1 ? n : n * recursiveCompute(n-1);
    }

    /**
     * 普通实现阶乘
     * @param n
     * @return
     */
    private static long normalCompute(long n) {
        long result = 1L;
        while(n > 1) {
            result *= n;
            n--;
        }
        return result;
    }

    /**
     * 尾递归，栈帧不需要保存每次运算的中间值
     * @param n
     * @return
     */
    private static long tailRecursiveCompute(long n, long m) {
        return m == 1? n: tailRecursiveCompute(n*m, m-1);
    }

    public static void main(String[] args) {
        System.out.println(normalCompute(6));
        System.out.println(recursiveCompute(6));
        System.out.println(tailRecursiveCompute(1,6));
    }
}
