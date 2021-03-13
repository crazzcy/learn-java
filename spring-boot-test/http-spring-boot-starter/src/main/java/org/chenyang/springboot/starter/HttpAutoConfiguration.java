package org.chenyang.springboot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author : ChenYang
 * @date : 2021-03-13 10:55 下午
 * @since :
 */
@Configuration
@EnableConfigurationProperties(HttpProperties.class)
public class HttpAutoConfiguration {
    @Resource
    HttpProperties httpProperties;

    @Bean
    @ConditionalOnMissingBean
    public HttpClient init() {
        HttpClient httpClient = new HttpClient();
        String url = httpProperties.getUrl();
        httpClient.setUrl(url);
        return httpClient;
    }
}
