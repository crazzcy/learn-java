package org.chenyang.study.java8.chapter09;

/**
 * @author ChenYang
 * @date 2019-04-26 15:20
 **/

public class D implements A {

    /**
     * 规则介绍：
     * （1） C继承自D，类的方法更具体，所以优先用D的方法
     * （2） D中没有复写方法，所以用的是A的默认方法
     * （3） 类中没有方法，就使用子接口的默认方法，由于B继承于A，则B更具体，使用B
     * （4） 如果去掉B继承于A，则产生菱形问题，C必须显示声明invoke()方法，否则会抛出一个编译错误 ↓
     *  Error:(8, 8) java: 类 com.chenyang.study.java8.chapter09.C从类型 com.chenyang.study.java8.chapter09.B 和 com.chenyang.study.java8.chapter09.A 中继承了invoke() 的不相关默认值
     * @param args
     */
    public static void main(String[] args) {

        new C().invoke();
    }
}
