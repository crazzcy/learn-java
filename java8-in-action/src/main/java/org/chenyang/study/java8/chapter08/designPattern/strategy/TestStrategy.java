package org.chenyang.study.java8.chapter08.designPattern.strategy;

import java.util.function.Function;

/**
 * @author ChenYang
 * @date 2019-04-26 0:08
 **/

public class TestStrategy {


    /**
     * 智能获取Echo
     * @return
     */
    private static Echo getEchoByAI(String msg) {
        if("hello".equals(msg)) {
            return new HelloEcho();
        } else if("bye".equals(msg)) {
            return new ByeEcho();
        } else {
            throw new RuntimeException("抱歉，我好像没听懂");
        }
    }


    private static Echo getEchoByAIFunction(String msg, Function<String, Echo> function) {
        return function.apply(msg);
    }

    public static void normalEcho(String msg) {
        Echo echo = getEchoByAI(msg);
        echo.echo(msg);
    }

    public static void functionalEcho(String msg) {
        Echo echo = getEchoByAIFunction(msg,(message)-> {
            if("hello".equals(message)) return new HelloEcho();
            else if("bye".equals(message)) return new ByeEcho();
            else throw new RuntimeException("抱歉，我好像没听懂");
        });
        echo.echo(msg);
    }



    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
        String msg1 = "hello";
        normalEcho(msg1);
        String msg2 = "bye";
        normalEcho(msg2);
        String msg3 = "hi";
        normalEcho(msg3);
        */
        functionalEcho("bye");
    }
}
