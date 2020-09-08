package org.chenyang.spring.aop.cglib;

/**
 * @author : ChenYang
 * @date : 2020-09-07 3:54 下午
 * @since :
 */
public class UserManagerImpl implements IUserManager {
    @Override
    public void addUser(String username, String password) {
        System.out.println("invoke UserManagerImpl add user:" + username + ",password:" + password);
    }

    @Override
    public void deleteUser(String username) {
        System.out.println("invoke UserManagerImpl delete user:" + username);
    }
}
