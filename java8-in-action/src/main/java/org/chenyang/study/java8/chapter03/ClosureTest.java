package org.chenyang.study.java8.chapter03;

import java.util.concurrent.Callable;

/**
 * 闭包测试
 * @author ChenYang
 * @date 2019-04-17 10:49
 **/

public class ClosureTest {

    public ClosureTest() {
        super();
    }


    public void invoke() throws Exception {
        // 闭包是指，可以读取函数内部局部变量的函数。也就是子函数。子函数外界的需要状态封闭，称为闭包。
        // 闭包要求这里的shereI 必须是final或者隐式final的，闭包外界的scope不可变，否则会引发线程安全问题，编译失败
        int shareI = 1;
        for (int i=0; i< 100; i ++) {
            Callable<Integer> c = () -> shareI + 1;
            c.call();
            System.out.println(shareI);
            // 这里如果不断在改变shareI的值，编译会报错，不符合闭包的要求
            // shareI+=1;
        }
    }

    public static void main(String [] args) throws Exception {
        new ClosureTest().invoke();
    }
}
