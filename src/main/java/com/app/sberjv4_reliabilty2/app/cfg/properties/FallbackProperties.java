package com.app.sberjv4_reliabilty2.app.cfg.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "fallback.recommendation")
@Data
public class FallbackProperties {
    private boolean enabled = true;
    private List<Long> defaultIds = List.of(1L, 2L, 3L, 4L);
}
