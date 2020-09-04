package org.chenyang.spring.aop;

/**
 * @author ChenYang
 * @date 2020-09-04 15:55
 **/

public class MyTourAgent implements Hotel {

    @Override
    public void orderRoom(String username) {
        System.out.println("TourAgent begin order room...");
        new HolidayDayInn().orderRoom(username);
        System.out.println("TourAgent help ["+username+"] order room success...");
    }
}
