package org.chenyang.study.java8.chapter08.designPattern.template;

/**
 * @author ChenYang
 * @date 2019-04-25 23:37
 **/

public class TestTemplate {

    private static void testOnlineBank1() {
        ABCOnlineBanking abc = new ABCOnlineBanking();
        abc.processCustomer(555);
    }

    private static void testOnlineBank2() {
        ABCOnlineBanking abc = new ABCOnlineBanking();
        abc.processCustomerFunctional(666, (id) -> System.out.println(id + "农业银行欢迎您的光临"));
    }

    public static void main(String[] args) {
        testOnlineBank1();
        testOnlineBank2();
    }
}
