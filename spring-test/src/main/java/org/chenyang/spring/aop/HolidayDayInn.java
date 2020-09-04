package org.chenyang.spring.aop;

/**
 * @author ChenYang
 * @date 2020-09-04 16:01
 **/

public class HolidayDayInn implements Hotel {
    @Override
    public void orderRoom(String username) {
        System.out.println("[" +username + "] has order a room in Holiday Inn..");
    }
}
