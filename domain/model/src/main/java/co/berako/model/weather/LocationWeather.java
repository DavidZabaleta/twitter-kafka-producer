package co.berako.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class LocationWeather {
    private String idLocationWeather;
    private Location location;
    private Current current;
    private Date dataRetrievedAt;
}
