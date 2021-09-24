package co.berako.api.handlers.weather;

import co.berako.api.handlers.weather.dto.LocationWeatherDTO;
import co.berako.api.handlers.weather.dto.LocationWeatherParamsDTO;
import co.berako.usecase.weather.PublishWeatherUseCase;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WeatherHandler {

    private final PublishWeatherUseCase publishWeatherUseCase;

    public @NotNull Mono<ServerResponse> publishCurrentWeatherByLocationKey(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(LocationWeatherParamsDTO.class)
                .map(LocationWeatherParamsDTO::convertDTOToDomain)
                .flatMap(publishWeatherUseCase::publishLocationWeather)
                .map(LocationWeatherDTO::convertDomainToDTO)
                .flatMap(locationWeatherDTO -> ServerResponse.ok().body(BodyInserters.fromValue(locationWeatherDTO)));
    }
}
