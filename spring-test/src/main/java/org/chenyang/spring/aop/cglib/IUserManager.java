package org.chenyang.spring.aop.cglib;

/**
 * @author : ChenYang
 * @date : 2020-09-07 3:52 下午
 * @since :
 */
public interface IUserManager {
    /**
     * 添加一个用户
     * @param username 用户名
     * @param password 密码
     */
    void addUser(String username, String password);

    /**
     * 删除一个用户
     * @param username 用户名
     */
    void deleteUser(String username);
}
