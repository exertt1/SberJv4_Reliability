package com.app.sberjv4_reliabilty2.app.cfg.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "spring.web.client")
@Data
public class RestClientProperties {
    private Duration connectionTimeout = Duration.ofSeconds(3);
    private Duration readTimeout = Duration.ofSeconds(5);
}
