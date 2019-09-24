package org.chenyang.study.java8.chapter08.designPattern.chainOfResponsibility;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 测试责任链
 * @author ChenYang
 * @date 2019-04-25 20:43
 **/

public class TestCOR {

    private String name;

    /**
     * 普通责任链模式应用
     */
    private static void sendMsg() {
        String msg = "oh, fuck";
        ProcessObject po = new ProcessObject();
        po.addHandler(new FilterFHandler());
        po.addHandler(new AddNameHandler());
        String result = (String) po.process(msg);
        System.out.println(result);
    }


    /**
     * 责任链模式的函数式编程处理方案
     */
    private static void sendMsgFunctional() {
        UnaryOperator<String> addName = (String msg) -> "匿名网友2：" + msg;
        UnaryOperator<String> filterMsg = (String msg) -> msg.replaceAll("fuck", "f***k");
        UnaryOperator<String> filterMsg2 = (String msg) -> msg += ";";
        // f(g(x))
        Function<String, String> pipeline = addName.andThen(filterMsg).andThen(filterMsg2);
        String msg = "oh, fuck";
        String result = pipeline.apply(msg);
        System.out.println(result);
    }

    public static void main(String[] args) {
        sendMsg();
        sendMsgFunctional();
    }
}
