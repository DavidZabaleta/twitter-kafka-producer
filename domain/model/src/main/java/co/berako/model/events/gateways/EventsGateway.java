package co.berako.model.events.gateways;

import co.berako.model.events.Event;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    <T> Mono<Boolean> emit(Event<T> event);
    Mono<Boolean> close();
}
