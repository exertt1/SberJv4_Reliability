package com.app.sberjv4_reliabilty2.app.cfg;

import com.app.sberjv4_reliabilty2.app.cfg.properties.ServicesProperties;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;

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
        System.out.println("🔥 Recommendation URL: " + url);
        int port = Integer.parseInt(url.split(":")[2]);
        System.out.println("🔥 Recommendation port: " + port);

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

    @Bean
    public WireMockServer getSlowRecommendationServer() {
        String url = servicesProperties.getSlowRecommendation().getUrl();
        int port = Integer.parseInt(url.split(":")[2]);

        if (port == 0) {
            port = 8083;
        }

        WireMockServer server = new WireMockServer(port);

        server.stubFor(get(urlEqualTo("/recommendations-slow"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[1, 2, 3, 4, 5, 6]")
                        .withFixedDelay(10000))
        );

        server.start();

        return server;
    }

    @Bean
    public WireMockServer getFailRecommendationServer() {
        String url = servicesProperties.getFailRecommendation().getUrl();

        int port = Integer.parseInt(url.split(":")[2]);

        if (port == 0) {
            port = 8084;
        }

        WireMockServer server = new WireMockServer(port);

        server.stubFor(get("/recommendation-fail")
                .inScenario("Retry Scenario")
                        .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(500)
                ).willSetStateTo("first failure")
        );

        server.stubFor(get("/recommendation-fail")
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("first failure")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(500))
                .willSetStateTo("second failure")
        );

        server.stubFor(get("/recommendation-fail")
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("second failure")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("[1, 2, 3, 4, 5, 6]"))
                );

        server.start();

        return server;
    }
}
