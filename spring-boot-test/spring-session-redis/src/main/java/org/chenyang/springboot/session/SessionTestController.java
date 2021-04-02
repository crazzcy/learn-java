package org.chenyang.springboot.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author : ChenYang
 * @date : 2021-03-18 10:08 上午
 * @since :
 */
@RestController
public class SessionTestController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String hello = (String) session.getAttribute("hello");
        if(hello == null) {
            session.setAttribute("hello", "Hello World");
            hello = (String) session.getAttribute("hello");
        }
        Principal principal = request.getUserPrincipal();
        String currentUserName = principal.getName();
        hello = hello == null? "null" : hello;
        String html = "<p>hello," + currentUserName + "</p>";
        html += "<p>session:hello=" + hello + "</p>";
        html += "<a href='/logout'>退出登录</a>";
        return html;
    }

}