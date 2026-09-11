package com.app.sberjv4_reliabilty2.app.cfg;

import com.app.sberjv4_reliabilty2.app.adapter.dev.DevFailRecommendationAdapter;
import com.app.sberjv4_reliabilty2.app.adapter.dev.DevGamesAdapter;
import com.app.sberjv4_reliabilty2.app.adapter.dev.DevRecommendationAdapter;
import com.app.sberjv4_reliabilty2.app.adapter.dev.DevSlowRecommendationAdapter;
import com.app.sberjv4_reliabilty2.app.adapter.prod.GamesAdapter;
import com.app.sberjv4_reliabilty2.app.adapter.prod.RecommendationAdapter;
import com.app.sberjv4_reliabilty2.app.cfg.properties.FallbackProperties;
import com.app.sberjv4_reliabilty2.app.cfg.properties.ServicesProperties;
import com.app.sberjv4_reliabilty2.app.services.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfiguration {
    private final ServicesProperties servicesProperties;

    public AppConfiguration(ServicesProperties servicesProperties) {
        this.servicesProperties = servicesProperties;
    }

//    @Bean
//    public RestClient getRecommendationRestClient() {
//        return RestClient.builder().baseUrl(servicesProperties.getRecommendation().getUrl()).build();
//    }

    @Profile("prod")
    @Bean
    public RecommendationAdapter getRecommendationAdapter(
            @Qualifier("recommendationRestClient") RestClient restClient
    ) {
        return new RecommendationAdapter(restClient);
    }

    @Profile("dev")
    @Bean
    public DevRecommendationAdapter getDevRecommendationAdapter(@Qualifier("recommendationRestClient") RestClient restClient) {
        return new DevRecommendationAdapter(restClient);
    }

//    @Bean
//    public RestClient getGamesRestClient() {
//        return RestClient.builder().baseUrl(servicesProperties.getGame().getUrl()).build();
//    }

    @Profile("prod")
    @Bean
    public GamesAdapter getGamesAdapter(@Qualifier("gameRestClient") RestClient restClient) {
        return new GamesAdapter(restClient);
    }

    @Profile("dev")
    @Bean
    public DevGamesAdapter getDevGamesAdapter(@Qualifier("gameRestClient") RestClient restClient) {
        return new DevGamesAdapter(restClient);
    }

//    @Bean
//    public RestClient getSlowRecommendationRestClient() {
//        return RestClient.builder().baseUrl(servicesProperties.getSlowRecommendation().getUrl()).build();
//    }

    @Profile("dev")
    @Bean
    public DevSlowRecommendationAdapter getDevSlowRecommendationAdapter(
            @Qualifier("gameSlowRecommendationRestClient") RestClient restClient
    ) {
        return new DevSlowRecommendationAdapter(restClient);
    }

    @Profile("dev")
    @Bean
    public DevFailRecommendationAdapter getDevFailRecommendationAdapter(
            @Qualifier("gameFailRecommendationRestClient") RestClient restClient,
            CircuitBreakerService circuitBreakerService,
            RetryService retryService,
            FallbackProperties fallbackProperties
    ) {
        return new DevFailRecommendationAdapter(
                restClient,
                circuitBreakerService,
                retryService,
                fallbackProperties
        );
    }

    @Profile("prod")
    @Bean
    public GamesRecommendationService getGamesRecommendationService(
            @Qualifier("getGamesAdapter") GamesPort gamesAdapter,
            @Qualifier("getRecommendationAdapter") RecommendationPort recommendationAdapter,
            @Qualifier("getDevSlowRecommendationAdapter") RecommendationPort slowRecommendationAdapter,
            @Qualifier("getDevFailRecommendationAdapter") RecommendationPort failRecommendationAdapter
    ) {
        return new GamesRecommendationService(
                gamesAdapter,
                recommendationAdapter,
                slowRecommendationAdapter,
                failRecommendationAdapter
        );
    }

    @Profile("dev")
    @Bean
    public GamesRecommendationService getDevGamesRecommendationService(
            @Qualifier("getDevGamesAdapter") GamesPort devGamesAdapter,
            @Qualifier("getDevRecommendationAdapter") RecommendationPort devRecommendationAdapter,
            @Qualifier("getDevSlowRecommendationAdapter") RecommendationPort devSlowRecommendationAdapter,
            @Qualifier("getDevFailRecommendationAdapter") RecommendationPort failRecommendationAdapter
    ) {
        return new GamesRecommendationService(
                devGamesAdapter,
                devRecommendationAdapter,
                devSlowRecommendationAdapter,
                failRecommendationAdapter
        );
    }
}
