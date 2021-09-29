package co.berako.events.kafkaproducer;

import co.berako.model.events.Event;
import co.berako.model.events.gateways.EventsGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.Future;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerAdapter implements EventsGateway {

    private final KafkaProducer<String, String> kafkaProducer;

    @Override
    public <T> Mono<Boolean> emit(Event<T> event) {
        return Mono.just(event)
                .map(KafkaProducerAdapterConverter::buildProducerRecord)
                .flatMap(this::sendRecord)
                .thenReturn(Boolean.TRUE);
    }

    @Override
    public Mono<Boolean> close() {
        return Mono.fromCallable(() -> {
            try {
                kafkaProducer.flush();
                kafkaProducer.close();
                return Boolean.TRUE;
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        });
    }

    private Mono<Future<RecordMetadata>> sendRecord(ProducerRecord<String, String> record) {
        return Mono.fromCallable(() -> kafkaProducer
                .send(record, (recordMetadata, e) -> {
                    if (nonNull(e))
                        log.error("Error while producing the message: ", e);

                    log.info("Received new metadata. \n"
                            .concat(String.format("Topic: %s \n", recordMetadata.topic()))
                            .concat(String.format("Partition: %s \n", recordMetadata.partition()))
                            .concat(String.format("Offset: %s \n", recordMetadata.offset()))
                            .concat(String.format("Timestamp: %s \n", recordMetadata.timestamp()))
                    );
                }));
    }
}
