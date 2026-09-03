package app.cfg.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "adapter")
@Data
public class AdapterProperties {
    private String mode = "dev";
    private Duration timeout = Duration.ofSeconds(5);
    private Duration connectionTimeout = Duration.ofSeconds(3);
    private Duration readTimeout = Duration.ofSeconds(5);
    private Retry retry = new Retry();

    @Data
    public static class Retry {
        private int maxAttempts = 3;
        private Duration backoffDelay = Duration.ofSeconds(1);
    }
}
