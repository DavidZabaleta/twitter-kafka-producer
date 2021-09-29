package co.berako.events.kafkaproducer;

import co.berako.model.events.Event;
import co.berako.model.events.gateways.EventsGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KafkaProducerAdapter extends KafkaProducerTemplate<String, String> implements EventsGateway {

    @Override
    public <T> Mono<Boolean> emit(Event<T> event) {
        return Mono.just(event)
                .map(KafkaProducerAdapterConverter::buildProducerRecord)
                .flatMap(this::sendRecord)
                .thenReturn(Boolean.TRUE);
    }

    @Override
    public Mono<Boolean> close() {
        return closeFlushing();
    }
}
