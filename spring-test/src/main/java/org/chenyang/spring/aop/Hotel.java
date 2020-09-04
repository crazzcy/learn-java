package org.chenyang.spring.aop;

/**
 * 酒店服务接口
 * @author ChenYang
 */
public interface Hotel {

    /**
     * 订房间
     * @param username 用户姓名
     */
    void orderRoom(String username);
}
