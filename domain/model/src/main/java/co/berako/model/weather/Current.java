package co.berako.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Current {
    private String tempCelsius;
    private String humidity;
    private String feelsLikeCelsius;
}
