package org.chenyang.spring.aop;

/**
 * 客房服务接口
 */
public interface Hotel {

    /**
     * 订房间
     * @param username
     */
    void orderRoom(String username);
}
