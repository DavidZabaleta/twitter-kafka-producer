package co.berako.api.handlers.twitter;

import co.berako.api.handlers.twitter.dto.TweetsTermsDTO;
import co.berako.usecase.twitter.PublishTweetsUseCase;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TwitterHandler {
    private final PublishTweetsUseCase publishTweetsUseCase;

    public @NotNull Mono<ServerResponse> publishLastTweetsByTerm(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(TweetsTermsDTO.class)
                .map(TweetsTermsDTO::convertDTOToDomain)
                .flatMap(publishTweetsUseCase::publish)
                .flatMap(aBoolean -> ServerResponse.ok().build());
    }
}
