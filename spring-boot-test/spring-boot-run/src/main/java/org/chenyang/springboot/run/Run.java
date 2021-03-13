package org.chenyang.springboot.run;

import org.chenyang.springboot.starter.HttpClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : ChenYang
 * @date : 2021-03-13 11:52 下午
 * @since :
 */
@Component
public class Run {
    @Resource
    HttpClient httpClient;

    public void printHtml() {
        System.out.println("http client request url：" + httpClient.getUrl());
        System.out.println("http body begin ====>");
        System.out.print(httpClient.getHtml());
        System.out.println("<====http body end");
    }
}
