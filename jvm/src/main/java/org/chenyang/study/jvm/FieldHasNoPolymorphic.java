package org.chenyang.study.jvm;

/**
 * @author : ChenYang
 * @date : 2021-04-08 5:23 下午
 * @since :
 */
public class FieldHasNoPolymorphic {

    static class Father {
        public int money = 1;

        public Father() {
            money = 2;
            // 调用是一次虚方法调用，实际执行的版本是Son::showMeTheMoney()方法
            // 但Son::showMeTheMoney()方法中访问的却是子类的money字段，这时候结果自然还是0，它要到子类的构造函数执行时才会被初始化。
            showMeTheMoney();
        }
        public void showMeTheMoney() {
            System.out.println("I am Father, i have $" + money);
        }
    }
    static class Son extends Father {
        public int money = 3;
        public Son() {
            money = 4;
            showMeTheMoney();
        }
        @Override
        public void showMeTheMoney() {
            System.out.println("I am Son, i have $" + money);
        }
    }
    public static void main(String[] args) {
        Father gay = new Son();
        System.out.println("This guy has $" + gay.money);
    }
}
