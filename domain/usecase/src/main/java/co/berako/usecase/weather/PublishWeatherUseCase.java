package co.berako.usecase.weather;

import co.berako.model.events.gateways.EventsGateway;
import co.berako.model.events.weather.LocationWeatherEvent;
import co.berako.model.weather.LocationWeather;
import co.berako.model.weather.LocationWeatherParams;
import co.berako.model.weather.gateways.WeatherClientRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PublishWeatherUseCase {

    private final WeatherClientRepository weatherClientRepository;
    private final EventsGateway eventsGateway;

    public Mono<LocationWeather> publishLocationWeather(LocationWeatherParams params) {
        return weatherClientRepository.getCurrentWeatherByLocationKey(params.getLocationKey())
                .flatMap(locationWeather -> eventsGateway.emit(new LocationWeatherEvent(locationWeather))
                        .thenReturn(locationWeather));
    }
}
