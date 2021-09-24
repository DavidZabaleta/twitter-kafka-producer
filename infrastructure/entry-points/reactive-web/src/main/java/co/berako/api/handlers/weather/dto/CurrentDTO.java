package co.berako.api.handlers.weather.dto;

import co.berako.model.weather.Current;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CurrentDTO {
    private String tempCelsius;
    private String humidity;
    private String feelsLikeCelsius;

    public static CurrentDTO convertDomainToDTO(Current current) {
        return CurrentDTO.builder()
                .tempCelsius(current.getTempCelsius())
                .humidity(current.getHumidity())
                .feelsLikeCelsius(current.getFeelsLikeCelsius())
                .build();
    }
}
