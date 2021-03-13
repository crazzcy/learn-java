package org.chenyang.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : ChenYang
 * @date : 2021-03-13 10:55 下午
 * @since :
 */
@ConfigurationProperties(prefix = "http")
public class HttpProperties {
    private String url = "http://www.baidu.com";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
