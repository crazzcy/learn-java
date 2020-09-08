package org.chenyang.spring.aop.dp;

/**
 * @author : ChenYang
 * @date : 2020-09-04 5:09 下午
 * @since :
 */
public class SubObjectImpl implements ISubObject{
    @Override
    public void execute() {
        System.out.println("SubObjectImpl executed!!");
    }
}
