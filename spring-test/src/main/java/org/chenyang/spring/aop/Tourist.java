package org.chenyang.spring.aop;

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

    public static void main(String[] args) {

        Tourist tourist = new Tourist("chenyang");
        MyTourAgent tourAgent = new MyTourAgent();
        tourAgent.orderRoom(tourist.getName());

    }
}
