package co.berako.model.events.weather;

import co.berako.model.enums.KafkaEventKeys;
import co.berako.model.events.Event;
import co.berako.model.weather.LocationWeather;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LocationWeatherEvent implements Event<LocationWeather> {

    private final LocationWeather locationWeather;

    @Override
    public String key() {
        return KafkaEventKeys.WEATHER_TOPIC.toString();
    }

    @Override
    public LocationWeather getValue() {
        return locationWeather;
    }
}
