package org.chenyang.study.jvm;

/**
 * @author : ChenYang
 * @date : 2021-04-08 5:01 下午
 * @since :
 */
public class MethodStaticDispatch {
    static class Human{

    }
    static class Man extends Human {

    }
    static class Woman extends Human {

    }

    public void sayHello(Human human) {
        System.out.println("hello human");
    }

    public void sayHello(Man man) {
        System.out.println("hello man");
    }

    public void sayHello(Woman woman) {
        System.out.println("hello woman");
    }

    public static void main(String[] args) {
        MethodStaticDispatch methodStaticDispatch = new MethodStaticDispatch();
        methodStaticDispatch.sayHello(new Man());
        methodStaticDispatch.sayHello(new Woman());
        System.out.println("--------------------");
        // Human是静态类型，Man是实际类型
        Human human1 = new Woman();
        Human human2 = new Man();
        methodStaticDispatch.sayHello(human1);
        methodStaticDispatch.sayHello(human2);

    }
}
