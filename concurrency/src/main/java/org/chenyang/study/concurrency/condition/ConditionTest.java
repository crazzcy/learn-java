package org.chenyang.study.concurrency.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ChenYang
 * @date 2019-03-16 15:18
 **/

public class ConditionTest {

    private Lock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private final static int MAX_SIZE = 16;
    private List<Coffee> products = new ArrayList<>(MAX_SIZE);


    public static void main(String[] args) {
        while(true) {

        }

    }


    class Coffee {
        private int coffeeId;

        public Coffee(int coffeeId) {
            this.coffeeId = coffeeId;
        }
    }

    class Consumer {
        public Consumer() {

        }
        public void buy() {
            lock.lock();
            while(products.size() == MAX_SIZE) {
                //notEmpty.
            }
            System.out.println("buy one cup of coffee");
        }
    }


    class Producer {
        public Producer() {

        }
        public void cook() {
            System.out.println("make one cup of coffee");
        }
    }

}
