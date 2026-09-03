package app.cfg;

import app.adapter.dev.DevGamesAdapter;
import app.adapter.dev.DevRecommendationAdapter;
import app.adapter.prod.GamesAdapter;
import app.adapter.prod.RecommendationAdapter;
import app.cfg.properties.ServicesProperties;
import app.services.GamesPort;
import app.services.GamesRecommendationService;
import app.services.RecommendationPort;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean
    public RestClient getRecommendationRestClient() {
        return RestClient.builder().baseUrl(servicesProperties.getRecommendation().getUrl()).build();
    }

    @Profile("prod")
    @Bean
    public RecommendationAdapter getRecommendationAdapter(
            @Qualifier("getRecommendationRestClient") RestClient restClient
    ) {
        return new RecommendationAdapter(restClient);
    }

    @Profile("dev")
    @Bean
    public DevRecommendationAdapter getDevRecommendationAdapter(@Qualifier("getRecommendationRestClient") RestClient restClient) {
        return new DevRecommendationAdapter(restClient);
    }

    @Bean
    public RestClient getGamesRestClient() {
        return RestClient.builder().baseUrl(servicesProperties.getGame().getUrl()).build();
    }

    @Profile("prod")
    @Bean
    public GamesAdapter getGamesAdapter(@Qualifier("getGamesRestClient") RestClient restClient) {
        return new GamesAdapter(restClient);
    }

    @Profile("dev")
    @Bean
    public DevGamesAdapter getDevGamesAdapter(@Qualifier("getGamesRestClient") RestClient restClient) {
        return new DevGamesAdapter(restClient);
    }

    @Profile("prod")
    @Bean
    public GamesRecommendationService getGamesRecommendationService(
            @Qualifier("getGamesAdapter") GamesPort gamesAdapter,
            @Qualifier("getRecommendationAdapter") RecommendationPort recommendationAdapter
    ) {
        return new GamesRecommendationService(gamesAdapter, recommendationAdapter);
    }

    @Profile("dev")
    @Bean
    public GamesRecommendationService getDevGamesRecommendationService(
            @Qualifier("getDevGamesAdapter") GamesPort gamesAdapter,
            @Qualifier("getDevRecommendationAdapter") RecommendationPort recommendationAdapter
    ) {
        return new GamesRecommendationService(gamesAdapter, recommendationAdapter);
    }


}
