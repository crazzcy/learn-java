package org.chenyang.study.java8.chapter08;

/**
 * @author ChenYang
 * @date 2019-04-25 14:39
 **/

public class TestLambda {

    /**
     * 匿名类和Lambda表达式中的this和super的含义是不同的。
     * 1、在匿名类中，this代表的是类自身，但是在Lambda中，它代表的是包含类。
     * 2、匿名类可以屏蔽包含类的变量，而Lambda表达式不能（它们会导致编译错误）
     */
    private static void testLambdaThis() {
        int a = 0;
        Runnable r = () -> {
            // 编译报错，Lambda表达式里，没有this
            //int a = 1;
            System.out.println(a);
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                // 匿名类就可以使用this
                int a = 3;
                System.out.println(a);
            }
        };


        new Thread(r).start();
        new Thread(r2).start();
    }

    private static void doSomeThing(Runnable r) {
        r.run();
    }

    private static void doSomeThing(Task task) {
        task.execute();
    }

    private static void testLambda2() {
        doSomeThing(new Task() {
            @Override
            public void execute() {
                System.out.println("task execute");
            }
        });
        doSomeThing(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable run");
            }
        });

        // 这句话，会引起歧义，不知道执行的是哪个 doSomething(Runnable) 和 doSomething(Task) 都匹配该类型
        // doSomeThing(() -> System.out.println("hahaha"));

        doSomeThing((Task)() -> System.out.println("hahaha"));

    }


    public static void main(String[] args) {
        testLambdaThis();
        testLambda2();
    }


    interface Task {
         void execute();
    }
}
