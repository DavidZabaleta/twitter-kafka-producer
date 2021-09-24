package co.berako.model.weather.gateways;

import co.berako.model.weather.LocationWeather;
import reactor.core.publisher.Mono;

public interface WeatherClientRepository {
    Mono<LocationWeather> getCurrentWeatherByLocationKey(String locationKey);
}
