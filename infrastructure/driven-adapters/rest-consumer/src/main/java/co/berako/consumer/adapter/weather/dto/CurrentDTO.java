package co.berako.consumer.adapter.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CurrentDTO {
    private String temp_c;
    private String humidity;
    private String feelslike_c;
}
