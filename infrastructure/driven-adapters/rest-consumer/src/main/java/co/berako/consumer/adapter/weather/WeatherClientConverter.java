package co.berako.consumer.adapter.weather;

import co.berako.consumer.adapter.weather.dto.CurrentDTO;
import co.berako.consumer.adapter.weather.dto.LocationDTO;
import co.berako.consumer.adapter.weather.dto.LocationWeatherDTO;
import co.berako.model.weather.Current;
import co.berako.model.weather.Location;
import co.berako.model.weather.LocationWeather;

import java.util.Date;

public class WeatherClientConverter {
    private WeatherClientConverter() {
    }

    public static LocationWeather convertLocationWeatherDTOToDomain(LocationWeatherDTO locationWeatherDTO) {
        return LocationWeather.builder()
                .location(convertLocationDTOToDomain(locationWeatherDTO.getLocation()))
                .current(convertCurrentDTOToDomain(locationWeatherDTO.getCurrent()))
                .dataRetrievedAt(new Date())
                .build();
    }

    private static Location convertLocationDTOToDomain(LocationDTO locationDTO) {
        return Location.builder()
                .city(locationDTO.getName())
                .region(locationDTO.getRegion())
                .country(locationDTO.getCountry())
                .build();
    }

    private static Current convertCurrentDTOToDomain(CurrentDTO currentDTO) {
        return Current.builder()
                .tempCelsius(currentDTO.getTemp_c())
                .humidity(currentDTO.getHumidity())
                .feelsLikeCelsius(currentDTO.getFeelslike_c())
                .build();
    }
}
