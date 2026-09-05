package com.app.sberjv4_reliabilty2.app.cfg.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mock.services")
@Data
public class ServicesProperties {
    private ServiceConfig recommendation = new ServiceConfig();
    private ServiceConfig game = new ServiceConfig();

    @Data
    public static class ServiceConfig {
        private String url;
    }
}
