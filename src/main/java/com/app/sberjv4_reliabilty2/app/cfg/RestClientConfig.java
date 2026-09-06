package com.app.sberjv4_reliabilty2.app.cfg;

import com.app.sberjv4_reliabilty2.app.cfg.properties.RestClientProperties;
import com.app.sberjv4_reliabilty2.app.cfg.properties.ServicesProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;


@Configuration
public class RestClientConfig {
    private final ServicesProperties servicesProperties;
    private final RestClientProperties restClientProperties;

    public RestClientConfig(RestClientProperties restClientProperties, ServicesProperties servicesProperties) {
        this.servicesProperties = servicesProperties;
        this.restClientProperties = restClientProperties;
    }

    @Bean
    @Qualifier("recommendationRestClient")
    public RestClient recommendationRestClient() {
        return createRestClient(servicesProperties.getRecommendation().getUrl());
    }

    @Bean
    @Qualifier("gameRestClient")
    public RestClient gameRestClient() {
        return createRestClient(servicesProperties.getGame().getUrl());
    }

    @Bean
    @Qualifier("gameSlowRecommendationRestClient")
    public RestClient recommendationSlowRestClient() {
        return createRestClient(servicesProperties.getSlowRecommendation().getUrl());
    }

    private RestClient createRestClient(String baseUrl) {

        System.out.println("⏰ ===== RestClient Config =====");
        System.out.println("  URL: " + baseUrl);
        System.out.println("  connectionTimeout: " + restClientProperties.getConnectionTimeout().toMillis() + "ms");
        System.out.println("  readTimeout: " + restClientProperties.getReadTimeout().toMillis() + "ms");
        System.out.println("⏰ =============================");
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout((int) restClientProperties.getReadTimeout().toMillis());
        factory.setConnectionRequestTimeout((int) restClientProperties.getConnectionTimeout().toMillis());

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }
}
