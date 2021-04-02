package org.chenyang.springboot.session.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author : ChenYang
 * @date : 2021-03-18 7:53 下午
 * @since :
 */
@ConfigurationProperties(prefix = "cy.session")
public class SessionProperties {
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration timeout = Duration.ofSeconds(SessionConstant.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS);

    private String namespace = "cy:session:";

    private String flushMode = "on_save";

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getFlushMode() {
        return flushMode;
    }

    public void setFlushMode(String flushMode) {
        this.flushMode = flushMode;
    }
}
