package co.berako.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Location {
    private String city;
    private String region;
    private String country;
}
