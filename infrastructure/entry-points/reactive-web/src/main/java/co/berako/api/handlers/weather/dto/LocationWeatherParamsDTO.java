package co.berako.api.handlers.weather.dto;

import co.berako.model.weather.LocationWeatherParams;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LocationWeatherParamsDTO {
    private String locationKey;
    private String email;

    public static LocationWeatherParams convertDTOToDomain(LocationWeatherParamsDTO locationWeatherParamsDTO) {
        return LocationWeatherParams.builder()
                .locationKey(locationWeatherParamsDTO.getLocationKey())
                .email(locationWeatherParamsDTO.getEmail())
                .build();
    }
}
