package co.berako.api.handlers.weather;

import co.berako.api.handlers.weather.dto.LocationWeatherDTO;
import co.berako.api.handlers.weather.dto.LocationWeatherParamsDTO;
import co.berako.api.validations.EntryMessageValidation;
import co.berako.usecase.weather.PublishWeatherUseCase;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class WeatherHandler {

    private final PublishWeatherUseCase publishWeatherUseCase;

    public @NotNull Mono<ServerResponse> publishCurrentWeatherByLocationKey(ServerRequest serverRequest) {
        return serverRequest
                .body((inputMessage, context) -> inputMessage.getBody()
                        .map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                        .flatMap(EntryMessageValidation::validateLocationWeatherParams)
                        .map(msg -> new Gson().fromJson(msg, LocationWeatherParamsDTO.class))
                        .next())
                .map(LocationWeatherParamsDTO::convertDTOToDomain)
                .flatMap(publishWeatherUseCase::publishLocationWeather)
                .map(LocationWeatherDTO::convertDomainToDTO)
                .flatMap(locationWeatherDTO -> ServerResponse.ok().body(BodyInserters.fromValue(locationWeatherDTO)));
    }
}
