package org.chenyang.spring.aop.sp;

/**
 * @author ChenYang
 * @date 2020-09-04 15:59
 **/
public class Tourist {

    /**
     * 游客姓名
     */
    private final String name;

    public Tourist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 游客寻找一个酒店
     */
    public void findHotel() {
        Hotel hotelProxy = new HotelProxy();
        // hotel的实现方法对上层透明
        hotelProxy.orderRoom(this.getName());
    }

    public static void main(String[] args) {
        Tourist tourist = new Tourist("chenyang");
        tourist.findHotel();
    }
}
