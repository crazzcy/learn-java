package org.chenyang.study.java8.chapter08.designPattern.template;

import java.util.function.Consumer;

/**
 * @author ChenYang
 * @date 2019-04-25 23:31
 **/

public abstract class OnlineBanking {

    /**
     * 模板化方法
     * @param id
     */
    public void processCustomer(int id) {
        System.out.println("数据库查询到id="+id+ "的用户");
        makeCustomerHappy(id);
    }

    abstract void makeCustomerHappy(int id);

    public void processCustomerFunctional(int id, Consumer<Integer> makeCustomerHappy) {
        System.out.println("数据库查询到id="+id+ "的用户");
        makeCustomerHappy.accept(id);
    }
}
