package co.berako.api.handlers.weather.dto;

import co.berako.model.weather.LocationWeather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LocationWeatherDTO {
    private String idLocationWeather;
    private LocationDTO location;
    private CurrentDTO current;
    private Date dataRetrievedAt;

    public static LocationWeatherDTO convertDomainToDTO(LocationWeather locationWeather) {
        return LocationWeatherDTO.builder()
                .idLocationWeather(locationWeather.getIdLocationWeather())
                .location(LocationDTO.convertDomainToDTO(locationWeather.getLocation()))
                .current(CurrentDTO.convertDomainToDTO(locationWeather.getCurrent()))
                .dataRetrievedAt(locationWeather.getDataRetrievedAt())
                .build();
    }
}
