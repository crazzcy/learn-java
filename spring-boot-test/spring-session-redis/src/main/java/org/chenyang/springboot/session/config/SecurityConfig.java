package org.chenyang.springboot.session.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : ChenYang
 * @date : 2021-03-18 3:45 下午
 * @since :
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    UserServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 使用自定义用户服务校验登录信息
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 用户登录信息校验使用自定义 userService
        // 还需要注意密码加密与验证需要使用同一种方式
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * 自定义处理登录处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login")
                .permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successForwardUrl("/hello")
                .and()
                .authorizeRequests()
                //permitAll不饶过安全验证
                .antMatchers( "/login", "/error", "/favicon.ico").permitAll()
                //定义哪些请求需要Url需要被保护
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout");
    }
}
