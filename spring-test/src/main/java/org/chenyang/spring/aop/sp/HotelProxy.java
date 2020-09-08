package org.chenyang.spring.aop.sp;

/**
 * 酒店代理，同时对外实现酒店接口
 * @author ChenYang
 * @date 2020-09-04 15:55
 **/
public class HotelProxy implements Hotel {

    private final Hotel hotel;

    public HotelProxy() {
        hotel = new HolidayDayInn();
    }

    @Override
    public void orderRoom(String username) {
        System.out.println("HotelAgent begin order room...");
        hotel.orderRoom(username);
        System.out.println("HotelAgent help ["+username+"] order room success...");
    }
}
