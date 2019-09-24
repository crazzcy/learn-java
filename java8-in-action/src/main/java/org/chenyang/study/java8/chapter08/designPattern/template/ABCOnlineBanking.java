package org.chenyang.study.java8.chapter08.designPattern.template;

/**
 * @author ChenYang
 * @date 2019-04-26 0:02
 **/

public class ABCOnlineBanking extends OnlineBanking {
    @Override
    void makeCustomerHappy(int id) {
        System.out.println("尊敬的" + id + "，中国农业银行欢迎您的光临");
    }
}
