package co.berako.api.handlers.weather.dto;

import co.berako.model.weather.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LocationDTO {
    private String city;
    private String region;
    private String country;

    public static LocationDTO convertDomainToDTO(Location location) {
        return LocationDTO.builder()
                .city(location.getCity())
                .region(location.getRegion())
                .country(location.getCountry())
                .build();
    }
}