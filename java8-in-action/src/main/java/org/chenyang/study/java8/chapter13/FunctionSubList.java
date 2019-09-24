package org.chenyang.study.java8.chapter13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ChenYang
 * @date 2019-06-06 15:00
 **/

public class FunctionSubList {

    /**
     * 打印子集，个人实现方案
     * 对运算控制不熟，使用位运算+字符串控制的傻办法实现
     * 列表的每个元素，均有两种可能，存在，或者不存在，因此子集可能存在的个数，应该等于 2^[listSize]
     * 具体每个子集的展示，可以映射到二进制的每一位，0表示不展示，1表示展示
     * 每个子集展示方案可以表示为:0000, 0001, 0010 ....
     * @param inputArgs
     */
    private static void myPrintSubList(List<Integer> inputArgs) {
        List<List<Integer>> outPutArgs = new ArrayList<>();

        // 多种可能
        int z = (int)Math.pow(2, inputArgs.size()) - 1;

        while(z >= 0) {
            // 转成二进制
            String binFlag = Integer.toBinaryString(z);

            // 控制二进制码的长度，一定要等于解剖列表的长度
            if(binFlag.length() - inputArgs.size() > 0) {
                // 截断转字节码
                binFlag = binFlag.substring(binFlag.length()-inputArgs.size());
            } else {
                // 补0
                while(binFlag.length() - inputArgs.size() < 0) {
                    binFlag = "0" + binFlag;
                }
            }

            // 字节码数组
            String[] zx = binFlag.split("");

            List<Integer> list = new ArrayList<>();

            for(int i=0; i<zx.length; i++) {
                if("1".equals(zx[i])) {
                    list.add(inputArgs.get(i));
                }
            }
            outPutArgs.add(list);
            System.out.println(Arrays.toString(list.toArray()));
            z--;
        }
        System.out.println(outPutArgs.size());

    }

    /**
     * chapter13里，使用归纳法实现打印子集
     * 归纳法的思想：先通过递归把列表打散。将当前元素，插入到目前的所有的子集里，再将合并后的子集，与合并前的子集累加，得到的是当前所有的子集
     * 函数式编程，需要注意的问题，首先是引用透明性，在无法保证内存不变性的情况下，为了保证函数可靠性，方案是先创建内存数据副本，对副本数据计算，再返回副本
     *
     */
    private static List<List<Integer>> mySubList2(List<Integer> inputArgs) {

        if(inputArgs.size() == 0) {
            List<List<Integer>> list = new ArrayList<>();
            list.add(Collections.emptyList());
            return list;
        }
        // 第一个
        Integer first = inputArgs.get(0);
        // 剩下的
        List<Integer> others = inputArgs.subList(1, inputArgs.size());

        List<List<Integer>> we1 = mySubList2(others);
        // 把第一个和后面所有的结果合并
        List<List<Integer>> we2 = insertAll(first, we1);
        // 再把子节点相加
        return concat(we1, we2);
    }

    /**
     * 这里不能修改we1和we2，要重新创建新的列表
     * @param we1
     * @param we2
     * @return
     */
    private static List<List<Integer>> concat(List<List<Integer>> we1, List<List<Integer>> we2) {
        List<List<Integer>> concatList = new ArrayList<>();
        concatList.addAll(we1);
        concatList.addAll(we2);
        return concatList;
    }

    /**
     * 这里不能修改we1，而是要新建一个新的列表
     * @param first
     * @param we1
     * @return
     */
    private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> we1) {
        List<List<Integer>> we2 = new ArrayList<>();

        we1.forEach((list) -> {
                    List<Integer> list2 = new ArrayList<>(list);
                    we2.add(list2);
                }
        );

        // 加上首条的
        we2.forEach(list->list.add(first));
        return we2;
    }

    public static void main(String[] args) {
        List<Integer> inputArgs = Arrays.asList(1,4,9,16);
        //myPrintSubList(inputArgs);
        mySubList2(inputArgs).forEach((list) -> System.out.println(Arrays.toString(list.toArray())));
    }
}
