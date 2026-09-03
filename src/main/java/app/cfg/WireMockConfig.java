package app.cfg;

import app.cfg.properties.ServicesProperties;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Configuration
@Profile("dev")
public class WireMockConfig {
    private final ServicesProperties servicesProperties;

    public WireMockConfig(ServicesProperties servicesProperties) {
        this.servicesProperties = servicesProperties;
    }

    @Bean
    public WireMockServer getRecommendationServer() {
        String url = servicesProperties.getRecommendation().getUrl();
        int port = Integer.parseInt(url.split(":")[2]);

        if (port == 0) {
            port = 8081;
        }

        WireMockServer server = new WireMockServer(port);

        server.stubFor(get(urlEqualTo("/recommendation"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[1, 2, 3, 4, 5, 6]")
                )
        );

        server.start();

        return server;
    }

    @Bean
    public WireMockServer getGameServer() {
        String url = servicesProperties.getGame().getUrl();
        int port = Integer.parseInt(url.split(":")[2]);

        if (port == 0) {
            port = 8082;
        }

        WireMockServer server = new WireMockServer(port);

        server.stubFor(get(urlPathMatching("/games/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "id": 1,
                                    "name": "Elden Ring"
                                }
                                """)
                )

        );

        server.start();

        return server;
    }
}
