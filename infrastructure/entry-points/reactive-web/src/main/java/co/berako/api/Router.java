package co.berako.api;

import co.berako.api.handlers.twitter.TwitterHandler;
import co.berako.api.handlers.weather.WeatherHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class Router implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> twitterRouterFunction(TwitterHandler twitterHandler) {
        return route(POST("/api-twitter"), twitterHandler::publishLastTweetsByTerm);
    }

    @Bean
    public RouterFunction<ServerResponse> weatherRouterFunction(WeatherHandler weatherHandler) {
        return route(POST("/api/weather"), weatherHandler::publishCurrentWeatherByLocationKey);
    }
}
