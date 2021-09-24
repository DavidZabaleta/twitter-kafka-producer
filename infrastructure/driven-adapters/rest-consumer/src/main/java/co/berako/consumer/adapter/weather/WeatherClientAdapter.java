package co.berako.consumer.adapter.weather;

import co.berako.consumer.adapter.weather.dto.LocationWeatherDTO;
import co.berako.model.exceptions.weather.LocationWeatherException;
import co.berako.model.weather.LocationWeather;
import co.berako.model.weather.gateways.WeatherClientRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.util.Objects.isNull;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WeatherClientAdapter implements WeatherClientRepository {

    private static final String CURRENT_URL = "/current.json";
    private static final String RAPIDAPI_HOST_HEADER = "x-rapidapi-host";
    private static final String RAPIDAPI_KEY_HEADER = "x-rapidapi-key";

    private final WebClient webClient;

    @Value(value = "${apis.weather.url}")
    private String url;

    @Value(value = "${apis.weather.headers.rapidapi-host}")
    private String rapidAPIHost;

    @Value(value = "${apis.weather.headers.credentials.rapidapi-key}")
    private String rapidAPIKey;

    @Override
    public Mono<LocationWeather> getCurrentWeatherByLocationKey(String locationKey) {
        return webClient
                .get()
                .uri(buildURI(locationKey))
                .header(RAPIDAPI_HOST_HEADER, rapidAPIHost)
                .header(RAPIDAPI_KEY_HEADER, rapidAPIKey)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    log.info("Weather API status code: ".concat(new Gson().toJson(clientResponse.statusCode().value())));

                    if (clientResponse.statusCode().is4xxClientError())
                        return Mono.error(LocationWeatherException.Type.INVALID_KEYWORD::build);

                    return clientResponse.bodyToMono(LocationWeatherDTO.class);
                })
                .flatMap(this::validateLocationWeatherDTO)
                .map(WeatherClientConverter::convertLocationWeatherDTOToDomain);
    }

    private Mono<LocationWeatherDTO> validateLocationWeatherDTO(LocationWeatherDTO locationWeatherDTO) {
        if (isNull(locationWeatherDTO.getCurrent()))
            return Mono.error(LocationWeatherException.Type.INVALID_BODY_RESPONSE::build);

        return Mono.just(locationWeatherDTO);
    }

    private URI buildURI(String locationKey) {
        return UriComponentsBuilder
                .fromUriString(url.concat(CURRENT_URL))
                .queryParam("q", locationKey)
                .build()
                .toUri();
    }
}
