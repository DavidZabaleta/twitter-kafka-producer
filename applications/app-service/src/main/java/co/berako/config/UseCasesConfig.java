package co.berako.config;

import co.berako.model.events.Event;
import co.berako.model.events.gateways.EventsGateway;
import co.berako.model.twitter.gateways.TwitterClientRepository;
import co.berako.model.weather.gateways.WeatherClientRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import reactor.core.publisher.Mono;

@Configuration
@ComponentScan(basePackages = "co.berako.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    private final TwitterClientRepository twitterClientRepository = tweetsKeyword -> Mono.empty();
    private final WeatherClientRepository weatherClientRepository = locationKey -> Mono.empty();
    private final EventsGateway eventsGateway = new EventsGateway() {
        @Override
        public <T> Mono<Boolean> emit(Event<T> event) {
            return null;
        }

        @Override
        public Mono<Boolean> close() {
            return null;
        }
    };

    @Bean
    @ConditionalOnMissingBean()
    public TwitterClientRepository tweetsClientRepository() {
        return twitterClientRepository;
    }

    @Bean
    @ConditionalOnMissingBean()
    public WeatherClientRepository weatherClientRepository() {
        return weatherClientRepository;
    }

    @Bean
    @ConditionalOnMissingBean()
    public EventsGateway getEventsGateway() {
        return eventsGateway;
    }
}
