package org.chenyang.springboot.session.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author : ChenYang
 * @date : 2021-03-18 3:43 下午
 * @since :
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String USERNAME = "cy";
    private static final String PASSWORD = "cy";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 如果是正式项目，我们需要从数据库数据数据，然后再校验，形式如下：
        // User user = userDAO.query(username);

        if (!username.equals(USERNAME)) {
            throw new UsernameNotFoundException(username);
        }
        // 封装成 Spring security 定义的 User 对象
        return User.builder()
                .username(username)
                .passwordEncoder(s -> passwordEncoder.encode(PASSWORD))
                .authorities(new SimpleGrantedAuthority("user"))
                .build();
    }
}
