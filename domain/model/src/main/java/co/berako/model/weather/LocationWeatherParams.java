package co.berako.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class LocationWeatherParams {
    private String locationKey;
    private String email;
}
